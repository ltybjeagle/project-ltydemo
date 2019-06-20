package com.liuty.maven.cache;

import javax.cache.Cache;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/19
 */
public abstract class CacheFacade {

    /**
     * 创建缓存
     * @param keyClazz
     * @param valueClazz
     * @param <K>
     * @param <V>
     * @return
     */
    public abstract <K, V> Cache<K, V> createCache(Class<K> keyClazz, Class<V> valueClazz);
}
