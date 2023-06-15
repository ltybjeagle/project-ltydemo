package com.sunny.maven.core.demo.ratelimiter;

import com.sunny.maven.core.annotation.ratelimiter.SPIRateLimiter;
import com.sunny.maven.core.ratelimiter.api.RateLimiterInvoker;
import org.junit.Test;
import org.springframework.core.io.support.SpringFactoriesLoader;

import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @author SUNNY
 * @description: 限流算法测试
 * @create: 2023/5/31 16:42
 */
public class RateLimiterTest {

    @Test
    public void testSlidingWindowRateLimiter() {
        List<RateLimiterInvoker> invokers = SpringFactoriesLoader.loadFactories(RateLimiterInvoker.class,
                Thread.currentThread().getContextClassLoader());
        AtomicReference<RateLimiterInvoker> invoker = new AtomicReference<>();
        invokers.forEach(rateLimiterInvoker -> {
            SPIRateLimiter spiRateLimiter = rateLimiterInvoker.getClass().getAnnotation(SPIRateLimiter.class);
            if ("slidingWindow".equals(spiRateLimiter.value())) {
                rateLimiterInvoker.init(5, 10000);
                invoker.set(rateLimiterInvoker);
            }
        });
        for (int i = 0; i < 10; i ++) {
            if (invoker.get().tryAcquire()) {
                System.out.println("执行任务");
            } else {
                System.out.println("被限流了");
            }
        }
    }

    @Test
    public void testFixedWindowRateLimiter() {
        List<RateLimiterInvoker> invokers = SpringFactoriesLoader.loadFactories(RateLimiterInvoker.class,
                Thread.currentThread().getContextClassLoader());
        AtomicReference<RateLimiterInvoker> invoker = new AtomicReference<>();
        invokers.forEach(rateLimiterInvoker -> {
            SPIRateLimiter spiRateLimiter = rateLimiterInvoker.getClass().getAnnotation(SPIRateLimiter.class);
            if ("fixedWindow".equals(spiRateLimiter.value())) {
                rateLimiterInvoker.init(5, 10000);
                invoker.set(rateLimiterInvoker);
            }
        });
        for (int i = 0; i < 10; i ++) {
            if (invoker.get().tryAcquire()) {
                System.out.println("执行任务");
            } else {
                System.out.println("被限流了");
            }
        }
    }
}
