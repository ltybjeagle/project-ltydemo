package com.sunny.maven.core.configuration;

import com.sunny.maven.core.common.context.SunnyApplicationContext;
import com.sunny.maven.core.handler.AsyncTaskHandler;
import com.sunny.maven.core.listener.GracefulShutdownListener;
import com.sunny.maven.core.listener.GracefulStartupListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
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
 * @create: 2022-01-17 14:36
 */
@Configuration
@EnableAsync
@EnableScheduling
@EnableConfigurationProperties(AsyncPoolProperties.class)
@ComponentScan(basePackages = {"com.sunny.maven.core.exception", "com.sunny.maven.core.filter"})
public class SunnyContextConfiguration implements AsyncConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(SunnyContextConfiguration.class);
    @Resource
    private AsyncPoolProperties asyncPoolProperties;
    /**
     * 注册上下文
     * @return
     */
    @Bean
    public SunnyApplicationContext sunnyApplicationContext() {
        return new SunnyApplicationContext();
    }

    /**
     * 服务启动监听
     * @return
     */
    @Bean
    public GracefulStartupListener gracefulStartupListener() {
        return new GracefulStartupListener();
    }

    /**
     * 服务关闭监听
     * @return
     */
    @Bean
    public GracefulShutdownListener gracefulShutdownListener() {
        return new GracefulShutdownListener();
    }

    /**
     * 设置缓存执行线程池
     * @return Executor
     */
    @Override
    @Bean(name = "asyncExecutor")
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(asyncPoolProperties.getCorePoolSize());
        executor.setMaxPoolSize(asyncPoolProperties.getMaxPoolSize());
        executor.setQueueCapacity(asyncPoolProperties.getQueueCapacity());
        executor.setKeepAliveSeconds(asyncPoolProperties.getKeepAliveSeconds());
        executor.setThreadNamePrefix(asyncPoolProperties.getThreadNamePrefix());
        // 设置队列满的情况下，直接使用主线程执行任务
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        return new AsyncTaskHandler(executor);
    }

    /**
     * 异步任务中异常处理
     * @return AsyncUncaughtExceptionHandler
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
//        return new SimpleAsyncUncaughtExceptionHandler();
        return (throwable, method, objects) -> {
            logger.error("======================={}=================={}", throwable.getMessage(), throwable);
            logger.error("exception method: {}", method.getName());
        };
    }
}
