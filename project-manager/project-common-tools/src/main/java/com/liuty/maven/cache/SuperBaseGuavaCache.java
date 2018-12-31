package com.liuty.maven.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

public abstract class SuperBaseGuavaCache<K, V> {

    /**
     * 缓存对象
     */
    private LoadingCache<K, V> cache;

    /**
     * 缓存最大容量，默认值10
     */
    protected Integer maximumSize = 10;

    /**
     * 缓存失效时长
     */
    protected Long duration = 10L;

    /**
     * 缓存失效单位，默认为s
     */
    protected TimeUnit timeUnit = TimeUnit.SECONDS;

    /**
     * 获取LoadingCache（单例模式）
     * @return
     */
    private LoadingCache<K, V> getCache() {
        if (cache == null) {
            synchronized (SuperBaseGuavaCache.class) {
                if (cache == null) {
                    CacheBuilder<Object, Object> tempCache = null;
                    if (duration > 0 && timeUnit != null) {
                        tempCache = CacheBuilder.newBuilder().expireAfterWrite(duration, timeUnit);
                    }
                    if (maximumSize > 0) {
                        tempCache.maximumSize(maximumSize);
                    }
                    cache = tempCache.build(new CacheLoader<K, V>() {
                        @Override
                        public V load(K key) throws Exception {
                            V target = getLoadData(key) != null ? getLoadData(key) : getLoadDataIfNull(key);
                            return target;
                        }
                    });
                }
            }
        }
        return cache;
    }

    /**
     * 返回加载到内存中的数据，一般从数据库中加载
     * @param key
     * @return
     */
    abstract V getLoadData(K key);

    /**
     * 调用getLoadData返回null值时自定义加载到内存的值
     * @param key
     * @return
     */
    abstract V getLoadDataIfNull(K key);

    /**
     * 清除缓存(可以批量清除，也可以清除全部)
     * @param keys
     */
    public void batchInvalidate(List<K> keys) {
        if (keys != null) {
            getCache().invalidateAll(keys);
        } else {
            getCache().invalidateAll();
        }
    }

    /**
     * 清除某个key的缓存
     * @param key
     */
    public void invalidateOne(K key) {
        getCache().invalidate(key);
    }

    /**
     * 写入缓存
     * @param key
     * @param value
     */
    public void putIntoCache(K key, V value) {
        getCache().put(key, value);
    }

    /**
     * 获取某个key对应的缓存
     * @param key
     * @return
     */
    public V getCacheValue(K key) {
        V cacheValue = null;
        try {
            cacheValue = getCache().get(key);
        } catch (ExecutionException ex) {
        }
        return cacheValue;
    }
}
