package com.sunny.maven.cache.template;

import com.sunny.maven.cache.service.ICacheFacadeService;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @author SUNNY
 * @description: 缓存对象模板
 * @create: 2022-09-20 13:43
 */
@Slf4j
public class CacheTemplate {
    /**
     * 缓存KEY前缀
     */
    private final String PRE_CACHE_KEY;
    /**
     * redis缓存对象
     */
    private ICacheFacadeService redisService;
    /**
     * 将结果value放入缓存，键值为key
     * @param key
     * @param value
     */
    public void put(String key, Object value) {
        redisService.put(PRE_CACHE_KEY + key, value);
    }
    /**
     * 根据键值获得结果
     * @param key
     * @return 如果键值对应的缓存不存在，返回null
     */
    public Object get(String key) {
        log.info("{} ——> get({}) ===> 查询缓存数据", Thread.currentThread().getName(), key);
        return redisService.get(PRE_CACHE_KEY + key);
    }
    /**
     * 获取缓存key集合
     * @return
     */
    public Set getKeys() {
        return redisService.keys(PRE_CACHE_KEY + "*");
    }
    /**
     * 从缓存中移除key
     * @param key
     */
    public void remove(String key) {
        redisService.remove(PRE_CACHE_KEY + key);
    }
    /**
     * 构造函数
     * @param preCacheKey 缓存KEY前缀
     */
    public CacheTemplate(String preCacheKey, ICacheFacadeService redisService) {
        this.PRE_CACHE_KEY = preCacheKey;
        this.redisService = redisService;
    }
}
