package com.sunny.maven.core.handle;

import com.sunny.maven.core.configuration.AsyncPoolMonitor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * @author SUNNY
 * @description: 异步任务装饰器
 * @create: 2022-09-06 15:36
 */
@Slf4j
public class AsyncTaskHandler implements AsyncTaskExecutor, InitializingBean, DisposableBean {
    private final AsyncTaskExecutor executor;
    private final String taskName;
    /**
     * 构造函数
     * @param executor 线程池
     */
    public AsyncTaskHandler(AsyncTaskExecutor executor, String taskName) {
        this.executor = executor;
        this.taskName = taskName;
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
            log.info("start {} Monitor!", taskName);
            AsyncPoolMonitor monitor =
                    new AsyncPoolMonitor(((ThreadPoolTaskExecutor) executor).getThreadPoolExecutor(), taskName,
                            60);
            new Thread(monitor).start();
        }
    }

    @Deprecated
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
        log.error("Caught async exception：{}", e.toString());
    }
}
