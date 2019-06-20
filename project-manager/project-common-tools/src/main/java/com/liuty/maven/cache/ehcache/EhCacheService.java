package com.liuty.maven.cache.ehcache;

import com.liuty.maven.cache.CacheFacade;
import com.liuty.maven.cache.ICacheService;
import org.springframework.cache.Cache;
import org.springframework.cache.ehcache.EhCacheCacheManager;

import java.util.Map;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/22
 */
public class EhCacheService<K, V> extends CacheFacade implements ICacheService<K, V> {

    private Cache cache;
    public EhCacheService(Class<K> keyClazz, Class<V> valueClazz) {
        EhcacheCreate ehcacheCreate = EhcacheCreate.getInstance();
        EhCacheCacheManager ehCacheCacheManager = ehcacheCreate.getEhCacheCacheManager();
        cache = ehCacheCacheManager.getCache("ehCache");
    }

    @Override
    public void put(K key, V value) {
        cache.put(key, value);
    }

    @Override
    public void batchPut(Map<K, V> dataValue) {

    }

    @Override
    public Object get(K key) {
        return cache.get(key);
    }

    @Override
    public void remove(K key) {
        cache.evict(key);
    }

    @Override
    public <K, V> javax.cache.Cache<K, V> createCache(Class<K> keyClazz, Class<V> valueClazz) {
        return null;
    }
}
