package com.liuty.maven.cache.mycache.store;

import javax.cache.CacheException;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/19
 */
public interface DataStore<K, V> {

    /**
     * 获取缓存
     * @param key
     * @return
     * @throws CacheException
     */
    ValueHolder<V> get(K key) throws CacheException;

    /**
     * 校验缓存是否存在
     * @param k
     * @return
     * @throws CacheException
     */
    boolean containsKey(K k) throws CacheException;

    /**
     * 保存缓存
     * @param key
     * @param value
     * @throws CacheException
     */
    void put(K key, V value) throws CacheException;

    /**
     * 清除缓存
     * @param key
     * @return
     * @throws CacheException
     */
    ValueHolder<V> remove(K key) throws CacheException;

    /**
     * 清空缓存
     * @throws CacheException
     */
    void clear() throws CacheException;
}
