package com.sunny.maven.template;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.commands.JedisCommands;

import java.util.*;

/**
 * @author SUNNY
 * @description: 基于redis实现分布式锁(支持可重入锁)
 * @create: 2022-01-19 16:21
 */
public class RedisWithReentrantLock {
    private static final Logger logger = LoggerFactory.getLogger(RedisWithReentrantLock.class);
    public static final String UNLOCK_LUA;

    static {
        UNLOCK_LUA = "if redis.call(\"get\", KEYS[1]) == ARGV[1] " +
                "then " +
                " return redis.call(\"del\", KEYS[1]) " +
                "else " +
                " return 0 " +
                "end ";
    }

    private ThreadLocal<Map<String, Integer>> lockers = new ThreadLocal<>();
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 获取分布式锁
     * @param key 作为锁的key值
     * @param expire 锁超时时间
     * @return
     */
    public boolean setLock(String key, long expire) {
        Map<String, Integer> refs = currentLockers();
        Integer refCnt = refs.get(key);
        if (refCnt != null) {
            refs.put(key, refCnt + 1);
            return true;
        }
        try {
            RedisCallback<Long> callback = (connection) -> {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                String uuid = UUID.randomUUID().toString();
                return commands.setnx(key, uuid);
            };
            Long result = redisTemplate.execute(callback);
            if (1L == (Objects.isNull(result) ? 0 : result)) {
                refs.put(key, 1);
                return true;
            }
        } catch (Exception ex) {
            logger.error("set redis lock fail, exception: {}", ex.getMessage());
        }
        return false;
    }

    /**
     * 获取锁信息
     * @param key 作为锁的key值
     * @return
     */
    public String getLock(String key) {
        try {
            RedisCallback<String> callback = (connection) -> {
                JedisCommands commands = (JedisCommands) connection.getNativeConnection();
                return commands.get(key);
            };
            return redisTemplate.execute(callback);
        } catch (Exception ex) {
            logger.error("get redis lock fail, exception: {}", ex.getMessage());
        }
        return "";
    }

    /**
     * 释放锁
     * @param key 作为锁的key值
     * @param requestId 锁key对应的value值
     * @return
     */
    public boolean releaseLock(String key, String requestId) {
        Map<String, Integer> refs = currentLockers();
        Integer refCnt = refs.get(key);
        if (refCnt == null) {
            return false;
        }
        refCnt -= 1;
        if (refCnt > 0) {
            refs.put(key, refCnt);
            return true;
        }
        refs.remove(key);
        /*
         * 释放锁的时候，有可能因为持锁之后方法执行时间大于锁的有效期，此时有可能已经被另外一个线程持有锁，
         * 所以不能直接删除
         */
        try {
            List<String> keys = new ArrayList<>();
            keys.add(key);
            List<String> args = new ArrayList<>();
            args.add(requestId);

            /*
             * 使用lua脚本删除redis中匹配value的key，
             * 可以避免由于方法执行时间过长而redis锁自动过期失效的时候误删其他线程的锁
             *
             * spring自带的执行脚本方法中，集群模式直接抛出不支持执行脚本的异常，
             * 所以只能拿到原redis的connection来执行脚本
             */
            RedisCallback<Long> callback = (connection) -> {
                Object nativeConnection = connection.getNativeConnection();
                /*
                 * 集群模式和单机模式虽然执行脚本的方法一样，但是没有共同的接口，所以只能分开执行
                 */
                if (nativeConnection instanceof JedisCluster) {
                    // 集群模式
                    return (Long) ((JedisCluster) nativeConnection).eval(UNLOCK_LUA, keys, args);
                } else if (nativeConnection instanceof Jedis) {
                    // 单机模式
                    return (Long) ((Jedis) nativeConnection).eval(UNLOCK_LUA, keys, args);
                }
                return 0L;
            };
            Long result = redisTemplate.execute(callback);
            return result != null && result > 0;
        } catch (Exception ex) {
            logger.error("release redis lock fail, exception: {}", ex.getMessage());
        }
        return false;
    }

    private Map<String, Integer> currentLockers() {
        Map<String, Integer> refs = lockers.get();
        if (refs != null) {
            return refs;
        }
        lockers.set(new HashMap<>());
        return lockers.get();
    }

    /**
     * 构造函数
     * @param redisTemplate redis模板对象
     */
    public RedisWithReentrantLock(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
