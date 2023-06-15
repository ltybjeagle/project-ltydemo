package com.sunny.maven.core.ratelimiter.api;

/**
 * @author SUNNY
 * @description: 限流调用器SPI，秒级单位限流
 * @create: 2023/5/31 11:25
 */
public interface RateLimiterInvoker {
    /**
     * 限流方法
     */
    boolean tryAcquire();

    /**
     * 释放资源
     */
    void release();

    /**
     * 在milliSeconds毫秒内最多允许通过permits个请求
     * @param permits 在milliSeconds毫秒内最多能够通过的请求个数
     * @param milliSeconds 毫秒数
     */
    default void init(int permits, int milliSeconds) {}
}
