package com.sunny.maven.rpc.ratelimiter.api;

import com.sunny.maven.rpc.constants.RpcConstants;
import com.sunny.maven.rpc.spi.annotation.SPI;

/**
 * @author SUNNY
 * @description: 限流调用器SPI，秒级单位限流
 * @create: 2023-03-08 13:53
 */
@SPI(value = RpcConstants.DEFAULT_RATELIMITER_INVOKER)
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
