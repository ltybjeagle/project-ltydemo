package com.sunny.maven.core.common.threadpool;

/**
 * @author SUNNY
 * @description: 自定义简易线程池接口
 * @create: 2023-04-07 14:12
 */
public interface Executor {
    /**
     * 执行任务
     */
    void execute(Runnable task);
    /**
     * 关闭线程池
     */
    void shutdown();
}
