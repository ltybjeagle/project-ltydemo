package com.liuty.maven.cache;

import java.util.Map;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/22
 */
public interface ICacheService<K, V> {

    /**
     * 将结果value放入缓存，键值为key
     * @param key
     * @param value
     */
    void put(K key, V value);

    /**
     * 批量保存缓存
     * @param dataValue
     */
    public void batchPut(Map<K, V> dataValue);

    /**
     * 根据键值获得结果
     * @param key
     * @return 如果键值对应的缓存不存在，返回null
     */
    Object get(K key);

    /**
     * 从缓存中移除key
     * @param key
     */
    void remove(K key);
}
