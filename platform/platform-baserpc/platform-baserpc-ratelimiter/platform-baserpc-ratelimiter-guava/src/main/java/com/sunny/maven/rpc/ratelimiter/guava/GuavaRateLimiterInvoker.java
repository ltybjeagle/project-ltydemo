package com.sunny.maven.rpc.ratelimiter.guava;

import com.google.common.util.concurrent.RateLimiter;
import com.sunny.maven.rpc.ratelimiter.base.AbstractRateLimiterInvoker;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SUNNY
 * @description: 基于Guava的限流策略
 * @create: 2023-03-10 18:01
 */
@Slf4j
@SPIClass
public class GuavaRateLimiterInvoker extends AbstractRateLimiterInvoker {
    private RateLimiter rateLimiter;
    @Override
    public boolean tryAcquire() {
        log.info("execute guava rate limiter...");
        return this.rateLimiter.tryAcquire();
    }

    @Override
    public void release() {
        //TODO ignore
    }

    @Override
    public void init(int permits, int milliSeconds) {
        super.init(permits, milliSeconds);
        // 转换成每秒钟最多允许的个数
        double permitsPerSecond = ((double) permits) / milliSeconds * 1000;
        this.rateLimiter = RateLimiter.create(permitsPerSecond);
    }
}
