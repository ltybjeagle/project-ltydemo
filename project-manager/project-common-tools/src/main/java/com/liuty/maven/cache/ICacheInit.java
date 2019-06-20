package com.liuty.maven.cache;

/**
 * @描述: 缓存初始化
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/15
 */
public interface ICacheInit<K, V> {

    /**
     * 获取缓存KEY类型
     * @return
     */
    Class<K> getKeyClass();

    /**
     * 获取缓存Value类型
     * @return
     */
    Class<V> getValueClass();

    /**
     * 设置缓存服务对象
     * @param cacheService
     */
    void setCacheService(ICacheService cacheService) ;

    /**
     * 初始化缓存
     */
    void initCache();
}
