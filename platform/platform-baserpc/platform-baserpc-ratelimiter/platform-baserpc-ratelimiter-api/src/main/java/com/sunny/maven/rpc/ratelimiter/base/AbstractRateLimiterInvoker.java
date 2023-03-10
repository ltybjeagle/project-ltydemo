package com.sunny.maven.rpc.ratelimiter.base;

import com.sunny.maven.rpc.ratelimiter.api.RateLimiterInvoker;

/**
 * @author SUNNY
 * @description: 抽象限流器
 * @create: 2023-03-08 14:04
 */
public abstract class AbstractRateLimiterInvoker implements RateLimiterInvoker {
    /**
     * 在milliSeconds毫秒内最多能够通过的请求个数
     */
    protected int permits;
    /**
     * 毫秒数
     */
    protected int milliSeconds;

    @Override
    public void init(int permits, int milliSeconds) {
        this.permits = permits;
        this.milliSeconds = milliSeconds;
    }
}
