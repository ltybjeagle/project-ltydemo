package com.sunny.maven.cache.service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: 缓存操作接口定义
 * @create: 2022-09-20 10:12
 */
public interface ICacheFacadeService {
    /**
     * 字符串类型设置（有失效时间）
     * @param key
     * @param value
     * @param expire
     * @param timeUnit
     */
    void put(String key, Object value, long expire, TimeUnit timeUnit);
    /**
     * 字符串类型设置
     * @param key
     * @param value
     */
    void put(String key, Object value);
    /**
     * 字符串类型查询
     * @param key
     * @return
     */
    Object get(String key);
    /**
     * 获取全部key
     * @param pattern
     * @return
     */
    Set<String> keys(String pattern);
    /**
     * 根据key删除
     * @param key
     */
    void remove(String key);
}
