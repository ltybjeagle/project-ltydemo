package com.sunny.maven.rpc.cache.result;

import com.sunny.maven.rpc.constants.RpcConstants;
import lombok.extern.slf4j.Slf4j;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author SUNNY
 * @description: 结果缓存管理器
 * @create: 2023-02-23 15:42
 */
@Slf4j
public class CacheResultManager<T> {

    /**
     * 缓存结果信息
     */
    private final Map<CacheResultKey, T> cacheResult = new ConcurrentHashMap<>(4096);
    /**
     * 扫描结果缓存的线程池
     */
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
    /**
     * 读写锁
     */
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    /**
     * 读锁
     */
    private final Lock readLock = lock.readLock();
    /**
     * 写锁
     */
    private final Lock writeLock = lock.writeLock();
    /**
     * 结果缓存过期时长，单位毫秒
     */
    private int cacheResultExpire;

    private static volatile CacheResultManager instance;

    private CacheResultManager(int cacheResultExpire, boolean enableResultCache) {
        this.cacheResultExpire = cacheResultExpire;
        if (enableResultCache) {
            this.startScanTask();
        }
    }

    public static <T> CacheResultManager<T> getInstance(int cacheResultExpire, boolean enableResultCache) {
        if (instance == null) {
            synchronized (CacheResultManager.class) {
                if (instance == null) {
                    log.info("创建缓存对象");
                    instance = new CacheResultManager(cacheResultExpire, enableResultCache);
                }
            }
        }
        return instance;
    }

    /**
     * 扫描结果缓存
     */
    private void startScanTask() {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            if (cacheResult.size() > 0) {
                writeLock.lock();
                try {
                    Iterator<Map.Entry<CacheResultKey, T>> iterator = cacheResult.entrySet().iterator();
                    while (iterator.hasNext()) {
                        Map.Entry<CacheResultKey, T> entry = iterator.next();
                        CacheResultKey cacheKey = entry.getKey();
                        // 当时间减去保存数据的缓存时间大于配置的时间间隔，则需要剔除缓存数据
                        if (System.currentTimeMillis() - cacheKey.getCacheTimeStamp() > cacheResultExpire) {
                            cacheResult.remove(cacheKey);
                        }
                    }
                } finally {
                    writeLock.unlock();
                }
            }
        }, 0, RpcConstants.RPC_SCAN_RESULT_CACHE_EXPIRE, TimeUnit.MILLISECONDS);
    }

    /**
     * 获取缓存中的数据
     */
    public T get(CacheResultKey key) {
        return cacheResult.get(key);
    }

    /**
     * 缓存数据
     */
    public void put(CacheResultKey key, T value) {
        writeLock.lock();
        try {
            cacheResult.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }
}
