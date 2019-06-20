package com.liuty.maven.cache.mycache.store.impl;

import com.liuty.maven.cache.mycache.store.ValueHolder;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/20
 */
public class LRUEntry<K, V> {
    public K key;
    public ValueHolder<V> value;
    public LRUEntry<K, V> pre;
    public LRUEntry<K, V> next;

    public LRUEntry(K key, ValueHolder<V> value) {
        this.key = key;
        this.value = value;
    }
}
