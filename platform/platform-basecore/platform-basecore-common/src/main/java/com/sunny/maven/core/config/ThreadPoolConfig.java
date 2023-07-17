package com.sunny.maven.core.config;

import com.sunny.maven.core.common.threadpool.CustomizeThreadPoolTaskExecutor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

/**
 * @author SUNNY
 * @description: 线程池配置
 * @create: 2023/7/12 22:54
 */
@Configuration
@EnableAsync
public class ThreadPoolConfig {

    /**
     * 声明一个线程池
     * @return 执行器
     */
    @Bean("CustomizeExecutor")
    public Executor asyncExecutor() {
        CustomizeThreadPoolTaskExecutor executor = new CustomizeThreadPoolTaskExecutor();
        // 核心线程数10：线程池创建时候初始化的线程数
        executor.setCorePoolSize(10);
        // 最大线程数10：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(10);
        // 缓冲队列500：用来缓冲执行任务的队列
        executor.setQueueCapacity(500);
        // 允许线程的空闲时间60秒：当超过了核心线程出之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);
        // 线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("customize-");
        executor.initialize();
        return executor;
    }
}
