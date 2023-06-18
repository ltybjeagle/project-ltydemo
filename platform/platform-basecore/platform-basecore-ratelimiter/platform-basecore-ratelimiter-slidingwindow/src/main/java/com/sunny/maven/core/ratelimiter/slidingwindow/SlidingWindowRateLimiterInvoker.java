package com.sunny.maven.core.ratelimiter.slidingwindow;

import com.sunny.maven.core.annotation.ratelimiter.SPIRateLimiter;
import com.sunny.maven.core.ratelimiter.base.AbstractRateLimiterInvoker;

/**
 * @author SUNNY
 * @description: 滑动窗口算法
 * @create: 2023/5/31 17:45
 */
@SPIRateLimiter(value = "slidingWindow")
public class SlidingWindowRateLimiterInvoker extends AbstractRateLimiterInvoker {
    /**
     * 分片窗口数
     */
    private final int shardNum = 10;
    /**
     * 各个窗口内请求计数
     */
    private int[] shardRequestCount;
    /**
     * 请求总数
     */
    private int totalPermits;
    /**
     * 当前窗口下标
     */
    private int shardId;
    /**
     * 每个小窗口大小，毫秒
     */
    private long tinyWindowSize;
    /**
     * 窗口右边界
     */
    private long windowBorder;

    @Override
    public boolean tryAcquire() {
        synchronized (this) {
            long currentTime = System.currentTimeMillis();
            if (currentTime > windowBorder) {
                do {
                    shardId = (++shardId) % shardNum;
                    totalPermits -= shardRequestCount[shardId];
                    shardRequestCount[shardId] = 0;
                    windowBorder += tinyWindowSize;
                } while (currentTime > windowBorder);
            }
            if (totalPermits < permits) {
                shardRequestCount[shardId]++;
                totalPermits++;
                return true;
            } else {
                return false;
            }
        }
    }

    @Override
    public void release() {

    }

    @Override
    public void init(int permits, int milliSeconds) {
        super.init(permits, milliSeconds);
        shardRequestCount = new int[shardNum];
        tinyWindowSize = milliSeconds / shardNum;
        windowBorder = System.currentTimeMillis();
    }
}
