package com.liuty.maven.cache.mycache.store.impl;

import com.liuty.maven.cache.mycache.store.ValueHolder;

import java.lang.ref.WeakReference;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/19
 */
public class WeakValueHolder<V> implements ValueHolder<V> {
    private WeakReference<V> v;

    public WeakValueHolder(V value) {
        this.v = new WeakReference<V>(value);
    }

    @Override
    public V value() {
        return this.v.get();
    }
}
