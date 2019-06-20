package com.liuty.maven.cache.mycache.store.impl;

import com.liuty.maven.cache.mycache.store.DataStore;
import com.liuty.maven.cache.mycache.store.ValueHolder;

import javax.cache.CacheException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @描述: 不回收策略存储
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/19
 */
public class BasicDataStore<K, V> implements DataStore<K, V> {
    private ConcurrentHashMap<K, ValueHolder<V>> cacheMap = new ConcurrentHashMap<>();
    @Override
    public ValueHolder<V> get(K key) throws CacheException {
        return cacheMap.get(key);
    }

    @Override
    public boolean containsKey(K k) throws CacheException {
        return cacheMap.containsKey(k);
    }

    @Override
    public void put(K key, V value) throws CacheException {
        ValueHolder<V> v = new BasicValueHolder<V>(value);
        cacheMap.put(key, v);
    }

    @Override
    public ValueHolder<V> remove(K key) throws CacheException {
        return cacheMap.remove(key);
    }

    @Override
    public void clear() throws CacheException {
        cacheMap.clear();
    }
}
