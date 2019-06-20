package com.liuty.maven.cache.mycache;

import com.liuty.maven.cache.mycache.store.DataStore;
import com.liuty.maven.cache.mycache.store.ValueHolder;

import javax.cache.Cache;
import javax.cache.CacheManager;
import javax.cache.configuration.CacheEntryListenerConfiguration;
import javax.cache.configuration.Configuration;
import javax.cache.integration.CompletionListener;
import javax.cache.processor.EntryProcessor;
import javax.cache.processor.EntryProcessorException;
import javax.cache.processor.EntryProcessorResult;
import java.util.Iterator;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/19
 */
public class MyCache<K, V> implements Cache<K, V> {
    private final DataStore<K, V> store ;
    // 缓存名称
    private final String cacheName;
    private final CacheManager cacheManager;
    private final Configuration<K, V> configuration;
    public MyCache(final CacheManager cacheManager, final String cacheName, final Configuration<K, V> configuration
            , final DataStore<K, V> dataStore) {
        this.cacheManager = cacheManager;
        this.cacheName = cacheName;
        this.configuration = configuration;
        this.store = dataStore;
    }

    @Override
    public V get(K k) {
        Optional<ValueHolder<V>> value = Optional.ofNullable(store.get(k));
        return value.isEmpty() ? null : value.get().value();
    }

    @Override
    public boolean containsKey(K k) {
        return store.containsKey(k);
    }

    @Override
    public void put(K k, V v) {
        store.put(k, v);
    }

    @Override
    public boolean remove(K k) {
        Optional<ValueHolder<V>> value = Optional.ofNullable(store.remove(k));
        return value.isEmpty();
    }

    @Override
    public V getAndRemove(K k) {
        Optional<ValueHolder<V>> value = Optional.ofNullable(store.remove(k));
        return value.isEmpty() ? null : value.get().value();
    }

    @Override
    public void removeAll() {
        store.clear();
    }

    @Override
    public void clear() {
        store.clear();
    }

    @Override
    public String getName() {
        return this.cacheName;
    }

    @Override
    public CacheManager getCacheManager() {
        return this.cacheManager;
    }

    @Override
    public <C extends Configuration<K, V>> C getConfiguration(Class<C> aClass) {
        return (C) this.configuration;
    }

    /**
     * 待扩展
     * @return
     */
    @Override
    public boolean isClosed() {
        return false;
    }

    /**
     * 待扩展
     * @param set
     * @return
     */
    @Override
    public Map<K, V> getAll(Set<? extends K> set) {
        return null;
    }

    /**
     * 待扩展
     * @param set
     * @param b
     * @param completionListener
     */
    @Override
    public void loadAll(Set<? extends K> set, boolean b, CompletionListener completionListener) {

    }

    /**
     * 待扩展
     * @param k
     * @param v
     * @return
     */
    @Override
    public V getAndPut(K k, V v) {
        return null;
    }

    /**
     * 待扩展
     * @param map
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> map) {

    }

    /**
     * 待扩展
     * @param k
     * @param v
     * @return
     */
    @Override
    public boolean putIfAbsent(K k, V v) {
        return false;
    }

    /**
     * 待扩展
     * @param k
     * @param v
     * @return
     */
    @Override
    public boolean remove(K k, V v) {
        return false;
    }

    /**
     * 待扩展
     * @param k
     * @param v
     * @param v1
     * @return
     */
    @Override
    public boolean replace(K k, V v, V v1) {
        return false;
    }

    /**
     * 待扩展
     * @param k
     * @param v
     * @return
     */
    @Override
    public boolean replace(K k, V v) {
        return false;
    }

    /**
     * 待扩展
     * @param k
     * @param v
     * @return
     */
    @Override
    public V getAndReplace(K k, V v) {
        return null;
    }

    /**
     * 待扩展
     * @param set
     */
    @Override
    public void removeAll(Set<? extends K> set) {

    }

    /**
     * 待扩展
     * @param k
     * @param entryProcessor
     * @param objects
     * @param <T>
     * @return
     * @throws EntryProcessorException
     */
    @Override
    public <T> T invoke(K k, EntryProcessor<K, V, T> entryProcessor, Object... objects)
            throws EntryProcessorException {
        return null;
    }

    /**
     * 待扩展
     * @param set
     * @param entryProcessor
     * @param objects
     * @param <T>
     * @return
     */
    @Override
    public <T> Map<K, EntryProcessorResult<T>> invokeAll(Set<? extends K> set
            , EntryProcessor<K, V, T> entryProcessor, Object... objects) {
        return null;
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

    /**
     * 待扩展
     * @param cacheEntryListenerConfiguration
     */
    @Override
    public void registerCacheEntryListener(CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration) {

    }

    /**
     * 待扩展
     * @param cacheEntryListenerConfiguration
     */
    @Override
    public void deregisterCacheEntryListener(CacheEntryListenerConfiguration<K, V> cacheEntryListenerConfiguration) {

    }

    /**
     * 待扩展
     * @return
     */
    @Override
    public Iterator<Entry<K, V>> iterator() {
        return null;
    }
}
