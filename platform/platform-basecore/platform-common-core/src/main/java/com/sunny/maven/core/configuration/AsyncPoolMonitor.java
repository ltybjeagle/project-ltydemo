package com.sunny.maven.core.configuration;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author SUNNY
 * @description: 异步线程池监听器
 * @create: 2022-09-06 17:17
 */
@Slf4j
public class AsyncPoolMonitor implements Runnable {

    private ThreadPoolExecutor executor;

    private int seconds;

    private String asyncName;

    private boolean run = true;

    public AsyncPoolMonitor(ThreadPoolExecutor executor, String asyncName, int delay) {
        this.executor = executor;
        this.asyncName = asyncName;
        this.seconds = delay;
    }

    public void shutdown(){
        this.run = false;
    }

    @Override
    public void run() {
        while (run) {
            log.info(
                    String.format(
                            "[%s monitor] [%d/%d] Active:%d, Completed:%d, Task:%d, isShutdown:%s, isTerminated:%s",
                            asyncName,
                            this.executor.getPoolSize(),
                            this.executor.getCorePoolSize(),
                            this.executor.getActiveCount(),
                            this.executor.getCompletedTaskCount(),
                            this.executor.getTaskCount(),
                            this.executor.isShutdown(),
                            this.executor.isTerminated()));
            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
