package com.sunny.maven.rpc.ratelimiter.counter;

import com.sunny.maven.rpc.ratelimiter.base.AbstractRateLimiterInvoker;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SUNNY
 * @description: 计数器限流
 * @create: 2023-03-08 14:11
 */
@Slf4j
@SPIClass
public class CounterRateLimiterInvoker extends AbstractRateLimiterInvoker {

    private final AtomicInteger currentCounter = new AtomicInteger(0);
    private volatile long lastTimeStamp = System.currentTimeMillis();
    private final ThreadLocal<Boolean> threadLocal = new ThreadLocal<>();

    @Override
    public boolean tryAcquire() {
        log.info("execute counter rate limiter...");
        // 获取当前时间
        long currentTimeStamp = System.currentTimeMillis();
        // 超过一个执行周期
        if (currentTimeStamp - lastTimeStamp >= milliSeconds) {
            lastTimeStamp = currentTimeStamp;
            currentCounter.set(0);
            return true;
        }
        // 当前请求数小于配置的数量
        if (currentCounter.incrementAndGet() <= permits) {
            threadLocal.set(true);
            return true;
        }
        return false;
    }

    @Override
    public void release() {
        if (threadLocal.get()) {
            try {
                currentCounter.decrementAndGet();
            } finally {
                threadLocal.remove();
            }
        }
    }
}
