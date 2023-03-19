package com.sunny.maven.rpc.ratelimiter.semaphore;

import com.sunny.maven.rpc.ratelimiter.base.AbstractRateLimiterInvoker;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SUNNY
 * @description: 基于Semaphore的限流策略
 * @create: 2023-03-10 14:49
 */
@Slf4j
@SPIClass
public class SemaphoreRateLimiterInvoker extends AbstractRateLimiterInvoker {

    private Semaphore semaphore;
    private final AtomicInteger currentCounter = new AtomicInteger(0);
    private volatile long lastTimeStamp = System.currentTimeMillis();

    @Override
    public boolean tryAcquire() {
        log.info("execute semaphore rate limiter...");
        // 获取当前时间
        long currentTimeStamp = System.currentTimeMillis();
        // 超过一个执行周期
        if (currentTimeStamp - lastTimeStamp >= milliSeconds) {
            // 重置窗口开始时间
            lastTimeStamp = currentTimeStamp;
            // 释放所有资源
            semaphore.release(currentCounter.get());
            // 重置计数
            currentCounter.set(0);
        }
        boolean result = semaphore.tryAcquire();
        // 成功获取资源
        if (result) {
            currentCounter.incrementAndGet();
        }
        return result;
    }

    @Override
    public void release() {
        //TODO ignore
        //semaphore.release();
    }

    @Override
    public void init(int permits, int milliSeconds) {
        super.init(permits, milliSeconds);
        this.semaphore = new Semaphore(permits);
    }
}
