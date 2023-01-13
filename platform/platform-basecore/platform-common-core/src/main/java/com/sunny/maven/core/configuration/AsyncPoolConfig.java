package com.sunny.maven.core.configuration;

import com.sunny.maven.core.handle.AsyncTaskHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author SUNNY
 * @description: 应用上下文注册
 * @create: 2022-09-06 15:20
 */
@Slf4j
@Configuration
@EnableAsync
@EnableScheduling
@EnableConfigurationProperties(AsyncPoolProperties.class)
public class AsyncPoolConfig implements AsyncConfigurer {
    private static final String ASYNC_EXECUTOR = "asyncExecutor";
    @Resource
    private AsyncPoolProperties asyncPoolProperties;

    /**
     * 设置异步任务线程池
     * @return Executor
     */
    @Override
    @Bean(name = ASYNC_EXECUTOR)
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(asyncPoolProperties.getCorePoolSize());
        executor.setMaxPoolSize(asyncPoolProperties.getMaxPoolSize());
        executor.setQueueCapacity(asyncPoolProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(asyncPoolProperties.getKeepAliveSeconds());
        executor.setThreadNamePrefix("AsyncThreadPool-");
        // 设置队列满的情况下，直接使用主线程执行任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 初始化
        executor.initialize();
        return new AsyncTaskHandler(executor, ASYNC_EXECUTOR);
    }

    /**
     * 异步任务中异常处理
     * @return AsyncUncaughtExceptionHandler
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            log.error("======================={}=================={}", throwable.getMessage(), throwable);
            log.error("exception method: {}", method.getName());
        };
    }
}
