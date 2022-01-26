package com.sunny.maven.cache.service;

import java.util.Set;

/**
 * @author SUNNY
 * @description: 缓存操作接口定义
 * @create: 2022-01-20 14:10
 */
public interface ICacheFacadeService {
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
