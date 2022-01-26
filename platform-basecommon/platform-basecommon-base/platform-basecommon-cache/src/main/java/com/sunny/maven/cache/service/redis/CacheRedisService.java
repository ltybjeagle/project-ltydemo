package com.sunny.maven.cache.service.redis;

import com.sunny.maven.cache.service.ICacheFacadeService;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: Redis缓存实现方案
 * @create: 2022-01-25 23:46
 */
public class CacheRedisService implements ICacheFacadeService {
    /**
     * 字符串类型设置（有失效时间）
     * @param key
     * @param value
     * @param expire
     * @param timeUnit
     */
    public void set(String key, Object value, long expire, TimeUnit timeUnit) {
        valueOperations.set(key, value, expire, timeUnit);
    }
    /**
     * 字符串类型设置
     * @param key
     * @param value
     */
    @Override
    public void put(String key, Object value) {
        valueOperations.set(key, value);
    }
    /**
     * 字符串类型查询
     * @param key
     * @return
     */
    @Override
    public Object get(String key) {
        return valueOperations.get(key);
    }
    /**
     * 批量获取
     * @param keys
     * @return
     */
    public List<Object> multiGet(Collection<String> keys) {
        return valueOperations.multiGet(keys);
    }
    /**
     * 使用管道获取批量数据
     * @param keys
     * @return
     */
    public List<Object> executePipelined(Collection<String> keys) {
        return redisTemplate.executePipelined((RedisConnection redisConnection) -> {
            StringRedisConnection srConn = (StringRedisConnection) redisConnection;
            for (String key : keys) {
                srConn.get(key);
            }
            return null;
        });
    }
    /**
     * 使用管道获取批量数据(批量获取hash要素)
     * @param keys
     * @param alias
     * @param hKey
     * @return
     */
    public List<Object> executePipelinedForHash(Collection<String> keys, String alias, String hKey) {
        return redisTemplate.executePipelined((RedisConnection redisConnection) -> {
            StringRedisConnection srConn = (StringRedisConnection) redisConnection;
            for (String key : keys) {
                Map<String, String> result = new HashMap<>();
                String hValue = srConn.hGet(key, hKey);
                result.put(alias, key);
                result.put(hKey, hValue);
            }
            return null;
        });
    }
    /**
     * 根据key删除
     * @param key
     */
    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }
    /**
     * 批量删除
     * @param keys
     */
    public void remove(Collection<String> keys) {
        redisTemplate.delete(keys);
    }
    /**
     * 获取全部key
     * @param pattern
     * @return
     */
    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
    }
    /**
     * HASH类型设置（有失效时间）
     * @param key
     * @param value
     * @param expire
     * @param timeUnit
     */
    public void putAll(String key, Map<String, Object> value, long expire, TimeUnit timeUnit) {
        hashOperations.putAll(key, value);
        if (expire > 0L) {
            redisTemplate.expire(key, expire, timeUnit);
        }
    }
    /**
     * HASH类型设置（有失效时间）
     * @param key
     * @param value
     */
    public void putAll(String key, Map<String, Object> value) {
        hashOperations.putAll(key, value);
    }
    /**
     * 设置哈希表某个要素值
     * @param key
     * @param hk
     * @param hv
     */
    public void put(String key, String hk, Object hv) {
        hashOperations.put(key, hk, hv);
    }

    /**
     * HASH类型查询
     * @param key
     * @return
     */
    public Map<String, Object> entries(String key) {
        return hashOperations.entries(key);
    }
    /**
     * 获取哈希表某个要素值
     * @param key
     * @param hk
     * @return
     */
    public Object hGet(String key, String hk) {
        return hashOperations.get(key, hk);
    }
    /**
     * Redis模板对象
     */
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * redis字符串对象
     */
    private ValueOperations<String, Object> valueOperations;
    /**
     * redis Hash对象
     */
    private HashOperations<String, String, Object> hashOperations;
    /**
     * 构造函数
     * @param redisTemplate Redis模板对象
     * @param valueOperations redis字符串对象
     * @param hashOperations redis Hash对象
     */
    public CacheRedisService(RedisTemplate<String, Object> redisTemplate,
                             ValueOperations<String, Object> valueOperations,
                             HashOperations<String, String, Object> hashOperations) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = valueOperations;
        this.hashOperations = hashOperations;
    }
}
