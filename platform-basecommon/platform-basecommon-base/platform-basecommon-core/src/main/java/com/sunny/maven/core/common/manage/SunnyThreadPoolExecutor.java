package com.sunny.maven.core.common.manage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: 扩展线程池
 * @create: 2021-11-18 12:13
 */
public class SunnyThreadPoolExecutor extends ThreadPoolExecutor {
    private static final Logger logger = LoggerFactory.getLogger(SunnyThreadPoolExecutor.class);

    /**
     * 构造函数
     * @param corePoolSize 核心线程数
     * @param maximumPoolSize 最大线程数
     * @param keepAliveTime 空闲时间
     * @param unit 空闲时间单位
     * @param workQueue 任务队列
     */
    public SunnyThreadPoolExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit,
                                   BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    /**
     * 运行前
     * @param thead 线程
     * @param runnable 任务
     */
    @Override
    protected void beforeExecute(Thread thead, Runnable runnable) {
        logger.info("beforeExecute MyThread Name: {}, TID: {}", ((Thread) runnable).getName(), thead.getId());
    }

    /**
     * 运行后
     * @param runnable 任务
     * @param throwable 异常
     */
    @Override
    protected void afterExecute(Runnable runnable, Throwable throwable) {
        logger.info("afterExecute TID: {}, afterExecute poolSize: {}", Thread.currentThread().getId(),
                this.getPoolSize());
    }
}
