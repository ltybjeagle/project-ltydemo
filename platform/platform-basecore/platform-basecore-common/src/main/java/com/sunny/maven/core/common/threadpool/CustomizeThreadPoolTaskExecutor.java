package com.sunny.maven.core.common.threadpool;

import org.slf4j.MDC;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author SUNNY
 * @description:
 * @create: 2023/7/12 22:44
 */
public final class CustomizeThreadPoolTaskExecutor extends ThreadPoolTaskExecutor {

    public CustomizeThreadPoolTaskExecutor() {
        super();
    }

    @Override
    public void execute(Runnable task) {
        super.execute(CustomizeThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public <T> Future<T> submit(Callable<T> task) {
        return super.submit(CustomizeThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }

    @Override
    public Future<?> submit(Runnable task) {
        return super.submit(CustomizeThreadMdcUtil.wrap(task, MDC.getCopyOfContextMap()));
    }
}
