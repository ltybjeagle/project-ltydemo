package com.sunny.maven.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

/**
 * @author ：SUNNY
 * @date ：Created in 2021/1/10 22:32
 * @description：缓存工具类
 * @modified By：
 * @version: 1.0.0
 */
@Slf4j
public class CaffeineLocalCacheUtil {

    /**
     * 利用缓存记数
     *
     * @param countSign    计数标识
     * @param cacheManager 缓存管理对象
     */
    public synchronized static void addCount(String countSign, CacheManager cacheManager) {
        if (StringUtils.isNotBlank(countSign) && cacheManager != null) {
            Cache countSignCache = cacheManager.getCache(countSign);
            Cache.ValueWrapper cacheVal = countSignCache.get("num");
            Long count = 1L;
            if (cacheVal != null) {
                count = (Long) cacheVal.get();
                count += 1;
            }
            countSignCache.put("num", count);
            log.info("===={}缓存数量值：{}", countSign, count);
        }
    }

    /**
     * 读取缓存数量
     *
     * @param cacheName    缓存名称
     * @param cacheManager 缓存管理
     * @return 缓存数量
     */
    public static Long getCount(String cacheName, CacheManager cacheManager) {
        Long count = 0L;
        if (StringUtils.isNotBlank(cacheName) && cacheManager != null) {
            Cache countSignCache = cacheManager.getCache(cacheName);
            Cache.ValueWrapper cacheVal = countSignCache.get("num");
            if (cacheVal != null) {
                count = (Long) cacheVal.get();
            }
        }
        log.info("===={}缓存数量值：{}", cacheName, count);
        return count;

    }

    /**
     * 获取缓存值
     *
     * @param cacheName    缓存名称
     * @param key          缓存key
     * @param cacheManager 缓存管理
     * @return 缓存值
     */
    public static Cache.ValueWrapper getCacheValue(String cacheName, String key, CacheManager cacheManager) {
        if (StringUtils.isBlank(cacheName) || StringUtils.isBlank(key) || cacheManager == null) {
            return null;
        }
        Cache cache = cacheManager.getCache(cacheName);
        if (cache == null) {
            log.info("----缓存：{}不存在", cacheName);
            return null;
        }
        Cache.ValueWrapper cacheVal = cache.get(key);
        if (cacheVal != null) {
            return cacheVal;
        }
        return null;
    }

    /**
     * 设置缓存值
     *
     * @param cacheName    缓存名称
     * @param key          缓存key
     * @param cacheManager 缓存管理器
     * @param cacheDataStr 缓存值字符串
     */
    public static boolean setCacheValue(String cacheName, String key, CacheManager cacheManager, String cacheDataStr) {
        if (StringUtils.isAnyBlank(cacheName, key, cacheDataStr) || cacheManager == null) {
            log.info("----缓存参数错误，不做数据缓存");
            return false;
        } else {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache == null) {
                log.info("----缓存：{}不存在", cacheName);
                return false;
            }
            cache.put(key, cacheDataStr);
            return true;
        }
    }

    /**
     * 缓存内容销毁
     *
     * @param cacheName    缓存名称
     * @param key          缓存key
     * @param cacheManager 缓存管理器
     * @return 销毁结果
     */
    public static boolean destroyCacheValue(String cacheName, String key, CacheManager cacheManager) {
        if (StringUtils.isAnyBlank(cacheName, key) || cacheManager == null) {
            log.info("----缓存参数错误，不做数据缓存");
            return false;
        } else {
            Cache cache = cacheManager.getCache(cacheName);
            if (cache == null) {
                log.info("----缓存：{}不存在", cacheName);
                return false;
            }
            cache.evictIfPresent(key);
            return true;
        }
    }
}
