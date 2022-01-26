package com.sunny.maven.core.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author SUNNY
 * @description: 线程池配置
 * @create: 2022-01-25 23:58
 */
@ConfigurationProperties("sunny.async.pool")
public class AsyncPoolProperties {
    /**
     * 核心线程数
     */
    private int corePoolSize = 50;
    /**
     * 最大线程数
     */
    private int maxPoolSize = 100;
    /**
     * 队列长度
     */
    private int queueCapacity = 10000;
    /**
     * 空闲时间
     */
    private int keepAliveSeconds = 3000;
    /**
     * 线程名称前缀
     */
    private String threadNamePrefix = "sunny-executor-";

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return maxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        this.maxPoolSize = maxPoolSize;
    }

    public int getQueueCapacity() {
        return queueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        this.queueCapacity = queueCapacity;
    }

    public int getKeepAliveSeconds() {
        return keepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        this.keepAliveSeconds = keepAliveSeconds;
    }

    public String getThreadNamePrefix() {
        return threadNamePrefix;
    }

    public void setThreadNamePrefix(String threadNamePrefix) {
        this.threadNamePrefix = threadNamePrefix;
    }
}
