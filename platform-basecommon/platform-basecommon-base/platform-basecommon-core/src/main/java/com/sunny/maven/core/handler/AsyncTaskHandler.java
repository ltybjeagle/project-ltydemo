package com.sunny.maven.core.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.AsyncTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author SUNNY
 * @description: 异步redis装饰器
 * @create: 2022-01-24 17:00
 */
public class AsyncTaskHandler implements AsyncTaskExecutor, InitializingBean, DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(AsyncTaskHandler.class);
    private final AsyncTaskExecutor executor;
    /**
     * 构造函数
     * @param executor 线程池
     */
    public AsyncTaskHandler(AsyncTaskExecutor executor) {
        this.executor = executor;
    }

    @Override
    public void destroy() throws Exception {
        if (executor instanceof DisposableBean) {
            DisposableBean bean = (DisposableBean) executor;
            bean.destroy();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (executor instanceof InitializingBean) {
            InitializingBean bean = (InitializingBean) executor;
            bean.afterPropertiesSet();
        }
    }

    @Override
    public void execute(Runnable runnable, long startTimeout) {
        executor.execute(createWrappedRunnable(runnable), startTimeout);
    }

    @Override
    public Future<?> submit(Runnable runnable) {
        return executor.submit(createWrappedRunnable(runnable));
    }

    @Override
    public <T> Future<T> submit(Callable<T> callable) {
        return executor.submit(createCallable(callable));
    }

    @Override
    public void execute(Runnable runnable) {
        executor.execute(createWrappedRunnable(runnable));
    }

    private Runnable createWrappedRunnable(final Runnable task) {
        return () -> {
            try {
                task.run();
            } catch (Exception ex) {
                handle(ex);
            }
        };
    }

    private <T> Callable<T> createCallable(final Callable<T> task) {
        return () -> {
            try {
                return task.call();
            } catch (Exception ex) {
                handle(ex);
                throw ex;
            }
        };
    }

    private void handle(Exception e) {
        logger.error("Caught async exception", e);
    }
}
