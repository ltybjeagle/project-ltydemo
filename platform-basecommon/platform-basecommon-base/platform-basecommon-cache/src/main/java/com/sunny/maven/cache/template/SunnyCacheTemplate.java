package com.sunny.maven.cache.template;

import com.sunny.maven.cache.service.ICacheFacadeService;

import java.util.Objects;
import java.util.Set;

/**
 * @author SUNNY
 * @description: 缓存对象模板
 * @create: 2022-01-19 15:49
 */
public class SunnyCacheTemplate {
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
     * 将结果value放入缓存，键值为key，如果键值已经存在，则退出不保存
     * @param key
     * @param value
     */
    public void putIfAbsent(String key, Object value) {
        String realKey = PRE_CACHE_KEY + key;
        if (Objects.isNull(redisService.get(realKey))) {
            redisService.put(key, value);
        }
    }
    /**
     * 根据键值获得结果
     * @param key
     * @return 如果键值对应的缓存不存在，返回null
     */
    public Object get(String key) {
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
    public SunnyCacheTemplate(String preCacheKey, ICacheFacadeService redisService) {
        this.PRE_CACHE_KEY = preCacheKey;
        this.redisService = redisService;
    }

}
