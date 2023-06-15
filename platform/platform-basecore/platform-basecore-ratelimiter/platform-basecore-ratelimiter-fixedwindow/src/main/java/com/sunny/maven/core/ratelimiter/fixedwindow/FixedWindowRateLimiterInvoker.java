package com.sunny.maven.core.ratelimiter.fixedwindow;

import com.sunny.maven.core.annotation.ratelimiter.SPIRateLimiter;
import com.sunny.maven.core.ratelimiter.base.AbstractRateLimiterInvoker;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SUNNY
 * @description: 固定窗口算法
 * @create: 2023/5/31 11:38
 */
@SPIRateLimiter(value = "fixedWindow")
public class FixedWindowRateLimiterInvoker extends AbstractRateLimiterInvoker {

    /**
     * 当前窗口通过的请求计数
     */
    private final AtomicInteger currentCounter = new AtomicInteger(0);
    /**
     * 窗口右边界
     */
    private volatile long windowBorder = System.currentTimeMillis();

    @Override
    public boolean tryAcquire() {
        // 获取当前时间
        long currentTime = System.currentTimeMillis();
        if (currentTime - windowBorder >= milliSeconds) {
            windowBorder = currentTime;
            currentCounter.set(0);
        }
        return currentCounter.incrementAndGet() <= permits;
    }

    @Override
    public void release() {

    }
}
