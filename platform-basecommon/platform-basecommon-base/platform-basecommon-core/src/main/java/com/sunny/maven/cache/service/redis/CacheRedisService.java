package com.sunny.maven.cache.service.redis;

import com.sunny.maven.cache.service.ICacheFacadeService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

import java.util.Set;

/**
 * @author SUNNY
 * @description: Redis缓存实现方案
 * @create: 2022-09-20 10:19
 */
public class CacheRedisService implements ICacheFacadeService {
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
     * 获取全部key
     * @param pattern
     * @return
     */
    @Override
    public Set<String> keys(String pattern) {
        return redisTemplate.keys(pattern);
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
     * Redis模板对象
     */
    private RedisTemplate<String, Object> redisTemplate;
    /**
     * redis字符串对象
     */
    private ValueOperations<String, Object> valueOperations;
    /**
     * 构造函数
     * @param redisTemplate Redis模板对象
     * @param valueOperations redis字符串对象
     */
    public CacheRedisService(RedisTemplate<String, Object> redisTemplate,
                             ValueOperations<String, Object> valueOperations) {
        this.redisTemplate = redisTemplate;
        this.valueOperations = valueOperations;
    }
}
