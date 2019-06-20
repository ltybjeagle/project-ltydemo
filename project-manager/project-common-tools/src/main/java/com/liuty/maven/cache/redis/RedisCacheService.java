package com.liuty.maven.cache.redis;

import com.liuty.maven.cache.CacheFacade;
import com.liuty.maven.cache.ICacheService;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import javax.cache.Cache;
import java.util.Map;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/23
 */
public class RedisCacheService<K, V> extends CacheFacade implements ICacheService<K, V> {

    private RedisTemplate<K, V> redisCacheTemplate;

    public RedisCacheService(Class<K> keyClazz, Class<V> valueClazz) {
        RedisCacheCreate redisCacheCreate = RedisCacheCreate.getInstance();
        LettuceConnectionFactory factory = redisCacheCreate.getLettuceConnectionFactory();
        redisCacheTemplate = new RedisTemplate<>();
        redisCacheTemplate.setKeySerializer(redisCacheCreate.getRedisKeySerializer());
        redisCacheTemplate.setValueSerializer(redisCacheCreate.getRedisValueSerializer());
        redisCacheTemplate.setConnectionFactory(factory);
    }

    @Override
    public void put(K key, V value) {
        redisCacheTemplate.opsForValue().set(key, value);
    }

    @Override
    public void batchPut(Map<K, V> dataValue) {
        redisCacheTemplate.opsForValue().multiSet(dataValue);
    }

    @Override
    public Object get(K key) {
        return redisCacheTemplate.opsForValue().get(key);
    }

    @Override
    public void remove(K key) {
        redisCacheTemplate.delete(key);
    }

    @Override
    public <K, V> Cache<K, V> createCache(Class<K> keyClazz, Class<V> valueClazz) {
        return null;
    }
}
