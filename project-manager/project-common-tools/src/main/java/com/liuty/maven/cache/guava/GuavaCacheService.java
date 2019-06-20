package com.liuty.maven.cache.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.liuty.maven.cache.CacheFacade;
import com.liuty.maven.cache.ICacheService;

import javax.cache.Cache;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/22
 */
public class GuavaCacheService<K, V> extends CacheFacade implements ICacheService<K, V> {

    private LoadingCache<K, V> cache;

    public GuavaCacheService(Class<K> keyClazz, Class<V> valueClazz) {
        CacheBuilder<Object, Object> tempCache = GuavaCacheCreate.getInstance().getCacheBuilder();
        cache = tempCache.build(new CacheLoader<K, V>() {
            @Override
            public V load(K key) throws Exception {
                //V target = getLoadData(key) != null ? getLoadData(key) : getLoadDataIfNull(key);
                //return target;
                return null;
            }
        });
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
        V cacheValue = null;
        try {
            cacheValue = cache.get(key);
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return cacheValue;
    }

    @Override
    public void remove(K key) {
        cache.invalidate(key);
    }

    @Override
    public <K, V> Cache<K, V> createCache(Class<K> keyClazz, Class<V> valueClazz) {
        return null;
    }
}
