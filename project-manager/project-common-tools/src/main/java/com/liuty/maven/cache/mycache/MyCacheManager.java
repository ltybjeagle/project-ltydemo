package com.liuty.maven.cache.mycache;

import com.liuty.maven.cache.mycache.store.DataStore;
import com.liuty.maven.cache.mycache.store.impl.BasicDataStore;
import com.liuty.maven.cache.mycache.store.impl.LRUDataStore;
import com.liuty.maven.cache.mycache.store.impl.WeakValueDataStore;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheManager;
import javax.cache.configuration.Configuration;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/19
 */
public class MyCacheManager implements CacheManager {
    /**
     * 缓存池
     */
    private ConcurrentHashMap<String, MyCache<?, ?>> caches = new ConcurrentHashMap<>();
    private CachingProvider cachingProvider;
    private String storeType;
    private final Lock lock = new ReentrantLock();

    @Override
    public <K, V, C extends Configuration<K, V>> Cache<K, V> createCache(String cacheName, C configuration)
            throws IllegalArgumentException {
        MyCache<?, ?> myCache = null;
        try {
            lock.lock();
            if (isClosed()) {
                throw new IllegalArgumentException();
            }
            // 校验cacheName、configuration对象不能为空

            myCache = caches.get(cacheName);
            if (myCache == null) {
                DataStore<K, V> dataStore = null;
                switch (storeType) {
                    case "LRU" :
                        dataStore = new LRUDataStore<>();
                        break;
                    case "BASIC" :
                        dataStore = new BasicDataStore<>();
                        break;
                    case "WEAK" :
                        dataStore = new WeakValueDataStore<>();
                        break;
                    default:
                        dataStore = new LRUDataStore<>();
                }
                myCache = new MyCache<K, V>(this, cacheName, configuration, dataStore);
                caches.put(cacheName, myCache);
            } else {
                throw new CacheException("缓存[" + cacheName + "]已经存在.");
            }
        } finally {
            lock.unlock();
        }
        return (Cache<K, V>) myCache;
    }

    @Override
    public <K, V> Cache<K, V> getCache(String cacheName, Class<K> keyClazz, Class<V> valueClazz) {
        if (isClosed()) {
            throw new IllegalArgumentException();
        }
        // 校验cacheName、keyClazz、valueClazz对象不能为空

        MyCache<K, V> myCache = (MyCache<K, V>) caches.get(cacheName);
        if (myCache == null) {
            return null;
        } else {
            Configuration<?, ?> configuration = myCache.getConfiguration(Configuration.class);
            if (configuration.getKeyType() != null && configuration.getKeyType().equals(keyClazz)) {
                return myCache;
            } else {
                throw new CacheException("缓存KEY类型不匹配，类型为[" + configuration.getKeyType() + "]");
            }
        }
    }

    @Override
    public CachingProvider getCachingProvider() {
        return this.cachingProvider;
    }

    @Override
    public boolean isClosed() {
        return false;
    }


    public MyCacheManager (CachingProvider cachingProvider, String storeType) {
        this.cachingProvider = cachingProvider;
        this.storeType = storeType;
    }

    /**
     * 待扩展
     * @return
     */
    @Override
    public URI getURI() {
        return null;
    }

    /**
     * 待扩展
     * @return
     */
    @Override
    public ClassLoader getClassLoader() {
        return null;
    }

    /**
     * 待扩展
     * @return
     */
    @Override
    public Properties getProperties() {
        return null;
    }

    /**
     * 待扩展
     * @param s
     * @param <K>
     * @param <V>
     * @return
     */
    @Override
    public <K, V> Cache<K, V> getCache(String s) {
        return null;
    }

    /**
     * 待扩展
     * @return
     */
    @Override
    public Iterable<String> getCacheNames() {
        return null;
    }

    /**
     * 待扩展
     * @param s
     */
    @Override
    public void destroyCache(String s) {

    }

    /**
     * 待扩展
     * @param s
     * @param b
     */
    @Override
    public void enableManagement(String s, boolean b) {

    }

    /**
     * 待扩展
     * @param s
     * @param b
     */
    @Override
    public void enableStatistics(String s, boolean b) {

    }

    /**
     * 待扩展
     */
    @Override
    public void close() {

    }

    /**
     * 待扩展
     * @param aClass
     * @param <T>
     * @return
     */
    @Override
    public <T> T unwrap(Class<T> aClass) {
        return null;
    }
}
