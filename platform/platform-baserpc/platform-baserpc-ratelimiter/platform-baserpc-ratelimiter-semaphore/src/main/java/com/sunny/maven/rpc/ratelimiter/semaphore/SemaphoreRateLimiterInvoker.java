package com.sunny.maven.rpc.ratelimiter.semaphore;

import com.sunny.maven.rpc.ratelimiter.base.AbstractRateLimiterInvoker;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;

/**
 * @author SUNNY
 * @description: 基于Semaphore的限流策略
 * @create: 2023-03-10 14:49
 */
@Slf4j
@SPIClass
public class SemaphoreRateLimiterInvoker extends AbstractRateLimiterInvoker {

    private Semaphore semaphore;

    @Override
    public boolean tryAcquire() {
        log.info("execute semaphore rate limiter...");
        return semaphore.tryAcquire();
    }

    @Override
    public void release() {
        semaphore.release();
    }

    @Override
    public void init(int permits, int milliSeconds) {
        super.init(permits, milliSeconds);
        this.semaphore = new Semaphore(permits);
    }
}
