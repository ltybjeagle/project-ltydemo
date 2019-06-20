package com.liuty.maven.cache.mycache.store.impl;

import com.liuty.maven.cache.mycache.store.ValueHolder;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/19
 */
public class BasicValueHolder<V> implements ValueHolder<V> {
    private V v;

    public BasicValueHolder(V value) {
        this.v = value;
    }

    @Override
    public V value() {
        return this.v;
    }
}
