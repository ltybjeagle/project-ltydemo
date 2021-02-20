package com.sunny.maven.configuration;

import com.github.benmanes.caffeine.cache.Caffeine;
import com.github.benmanes.caffeine.cache.RemovalCause;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.caffeine.CaffeineCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author ：SUNNY
 * @date ：Created in 2021/1/10 20:48
 * @description：Caffeine配置类
 * @modified By：
 * @version: 1.0.0
 */
@Slf4j
@Configuration
public class CaffeineCacheConfig extends CachingConfigurerSupport {

    /**
     * 自定义(本地缓存)
     */
    @Value("${spring.cache.caffeine.special.caches}")
    private String specialCachesStr;

    @Bean
    public CacheManager caffeineCacheManager() {
        SimpleCacheManager cacheManager = new SimpleCacheManager();
        List specialCaches = new ArrayList<>();
        String[] spCaches = specialCachesStr.split(",");
        if (ArrayUtils.isNotEmpty(spCaches)) {
            for (String spCache : spCaches) {
                String[] cacheParam = spCache.split("[+]");
                if (cacheParam.length == 3) {
                    String cacheName = cacheParam[0].trim();
                    Long cacheMaxSize = Long.parseLong(cacheParam[1].trim());
                    Long cacheDuration = Long.parseLong(cacheParam[2].trim());

                    //初始化
                    @NonNull
                    Caffeine<Object, Object> dictLoadCache = Caffeine.newBuilder();
                    dictLoadCache.removalListener((Object key, Object value, RemovalCause cause) -> {
                        //这里可以增加缓存消失的通知等等
                        log.info("Key %s was removed value=%s%n", key, value);
                    });
                    //超时时间设置
                    if (cacheDuration != null && cacheDuration > 0) {
                        dictLoadCache.expireAfterWrite(cacheDuration, TimeUnit.SECONDS);
                    }
                    //这里是设置数量，驱逐就根据数量，也可以用大小限定缓存，逐出策略有三种，大小、权重、时间
                    if (cacheMaxSize != null) {
                        dictLoadCache.maximumSize(cacheMaxSize);
                    }
                    log.info("----初始化特异缓存:{},最大值{}，时长{}",
                            cacheName, cacheMaxSize, cacheDuration);
                    CaffeineCache cafCache = new CaffeineCache(cacheName,
                            dictLoadCache.build(key -> cacheName));
                    specialCaches.add(cafCache);
                }
            }
        }
        cacheManager.setCaches(specialCaches);
        return cacheManager;
    }
}
