package com.liuty.maven.cache.mycache;

import com.liuty.maven.cache.CacheFacade;
import com.liuty.maven.cache.ICacheService;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.MutableConfiguration;
import javax.cache.spi.CachingProvider;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/22
 */
public class MyCacheService<K, V> extends CacheFacade implements ICacheService<K, V> {

    private static AtomicLong atomicLong = new AtomicLong();
    private Cache<K, V> cache;

    public MyCacheService(Class<K> keyClazz, Class<V> valueClazz) {
        /**
         * 通过SPI接口方式获取实例
         * ServiceLoader<CachingProvider> serviceLoaders = ServiceLoader.load(CachingProvider.class);
         */
        CachingProvider cachingProvider = MyCachingProvider.getInstance();
        CacheManager cacheManager = cachingProvider.getCacheManager();
        MutableConfiguration<K, V> mutableConfiguration = new MutableConfiguration<>();
        mutableConfiguration.setTypes(keyClazz, valueClazz);
        cache = cacheManager.createCache("ehCache" + atomicLong.incrementAndGet(), mutableConfiguration);
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
        cache.remove(key);
    }

    @Override
    public <K, V> Cache<K, V> createCache(Class<K> keyClazz, Class<V> valueClazz) {
        return null;
    }
}
