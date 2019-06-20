package com.liuty.maven.cache.mycache.store.impl;

import com.liuty.maven.cache.mycache.store.DataStore;
import com.liuty.maven.cache.mycache.store.ValueHolder;

import javax.cache.CacheException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @描述: 基于LRU实现逻辑存储
 *  清除策略：最近最少使用清楚策略
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/20
 */
public class LRUDataStore<K, V> implements DataStore<K, V> {
    private ConcurrentHashMap<K, LRUEntry<K, V>> cacheMap = new ConcurrentHashMap<>();
    private LRUEntry<K, V> head;
    private LRUEntry<K, V> end;
    // 存储对象数
    private int limit = 1000;

    @Override
    public ValueHolder<V> get(K key) throws CacheException {
        LRUEntry<K, V> lruEntry = cacheMap.get(key);
        if (lruEntry == null) {
            return null;
        }
        refreshEntry(lruEntry);
        return lruEntry.value;
    }

    @Override
    public boolean containsKey(K k) throws CacheException {
        return cacheMap.containsKey(k);
    }

    @Override
    public void put(K key, V value) throws CacheException {
        LRUEntry<K, V> lruEntry = cacheMap.get(key);
        if (lruEntry == null) {
            if (cacheMap.size() >= limit) {
                removeEntry(head);
                cacheMap.remove(head.key);
            }
            lruEntry = new LRUEntry<K, V>(key, new BasicValueHolder<V>(value));
            addEntry(lruEntry);
            cacheMap.put(key, lruEntry);
        } else {
            lruEntry.value = new BasicValueHolder<V>(value);
            refreshEntry(lruEntry);
        }
    }

    @Override
    public ValueHolder<V> remove(K key) throws CacheException {
        LRUEntry<K, V> lruEntry = cacheMap.remove(key);
        if (lruEntry == null) {
            return null;
        }
        removeEntry(lruEntry);
        return lruEntry.value;
    }

    @Override
    public void clear() throws CacheException {
        cacheMap.clear();
        head = null;
        end = null;
    }

    /**
     * 删除节点
     * @param lruEntry
     * @return
     */
    private K removeEntry(LRUEntry<K, V> lruEntry) {
        if (end == lruEntry) {
            end = lruEntry.pre;
        } else if (head == lruEntry) {
            head = lruEntry.next;
        } else {
            lruEntry.pre.next = lruEntry.next;
            lruEntry.next.pre = lruEntry.pre;
        }
        return lruEntry.key;
    }

    /**
     * 尾部添加节点
     * @param lruEntry
     */
    private void addEntry(LRUEntry<K, V> lruEntry) {
        if (end != null) {
            end.next = lruEntry;
            lruEntry.pre = end;
            lruEntry.next = null;
        }
        end = lruEntry;
        if (head == null) {
            head = lruEntry;
        }
    }

    /**
     * 刷新被访问的节点位置
     * @param lruEntry
     */
    private void refreshEntry(LRUEntry<K, V> lruEntry) {
        if (end == lruEntry) {
            return ;
        }
        removeEntry(lruEntry);
        addEntry(lruEntry);
    }
}
