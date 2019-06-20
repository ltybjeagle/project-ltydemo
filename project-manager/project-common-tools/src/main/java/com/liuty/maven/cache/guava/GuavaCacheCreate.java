package com.liuty.maven.cache.guava;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.RemovalListener;
import com.google.common.cache.RemovalNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/22
 */
public class GuavaCacheCreate {

    private static final Logger logger = LoggerFactory.getLogger(GuavaCacheCreate.class);
    private CacheBuilder<Object, Object> tempCache = null;
    /**
     * 缓存最大容量
     */
    private Integer maximumSize = 1000;

    /**
     * 缓存失效时长
     */
    private Long duration = 10L;

    /**
     * 缓存失效单位，默认为s
     */
    private TimeUnit timeUnit = TimeUnit.SECONDS;

    public static GuavaCacheCreate getInstance() {
        return GuavaCacheCreateInstance.GUAVA_CACHE_CREATE;
    }

    public CacheBuilder<Object, Object> getCacheBuilder() {
        return this.tempCache;
    }

    private GuavaCacheCreate() {
        if (duration > 0 && timeUnit != null) {
            tempCache = CacheBuilder.newBuilder().expireAfterWrite(duration, timeUnit);
        }
        if (maximumSize > 0) {
            tempCache.maximumSize(maximumSize);
        }
        tempCache.recordStats();
        tempCache.removalListener(new RemovalListener<Object, Object>() {
            @Override
            public void onRemoval(RemovalNotification<Object, Object> notification) {
                logger.info("{} was removed, case is {}", notification.getKey(), notification.getCause());
            }
        });
    }

    private static class GuavaCacheCreateInstance {
        private static final GuavaCacheCreate GUAVA_CACHE_CREATE  = new GuavaCacheCreate();
    }
}
