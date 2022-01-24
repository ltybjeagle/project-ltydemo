package com.sunny.maven.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Objects;

/**
 * @author SUNNY
 * @description: 缓存配置信息
 * @create: 2022-01-19 10:50
 */
@ConfigurationProperties("sunny.cache")
public class SunnyCacheProperties {
    /**
     * 缓存数据库
     */
    private Integer cacheDatabase;
    /**
     * 缓存地址
     */
    private String cacheHost;
    /**
     * 缓存端口
     */
    private Integer cachePort;
    /**
     * 缓存密码
     */
    private String cachePwd;
    /**
     * 缓存 超时时间
     */
    private Long cacheTimeout;
    /**
     * 缓存模式（单机、哨兵）
     */
    private String cacheMaster;
    /**
     * 缓存 哨兵地址
     */
    private String cacheNodes;
    /**
     * 缓存 最大连接数
     */
    private Integer cacheMaxActive;
    /**
     * 缓存 最大空闲连接数
     */
    private Integer cacheMaxIdle;
    /**
     * 缓存最 小空闲连接数
     */
    private Integer cacheMinIdle;
    /**
     * 缓存 当池内没有可用连接时，最大等待时间
     */
    private Long cacheMaxWait;
    /**
     * 缓存 线程池设置
     */
    private AsyncCacheProperties asyncCache;

    public AsyncCacheProperties getAsyncCache() {
        return asyncCache;
    }

    public void setAsyncCache(AsyncCacheProperties asyncCache) {
        this.asyncCache = asyncCache;
    }

    public Integer getCacheDatabase() {
        return Objects.isNull(cacheDatabase) ? 0 : cacheDatabase;
    }

    public void setCacheDatabase(Integer cacheDatabase) {
        this.cacheDatabase = cacheDatabase;
    }

    public String getCacheHost() {
        return Objects.isNull(cacheHost) ? "127.0.0.1" : cacheHost;
    }

    public void setCacheHost(String cacheHost) {
        this.cacheHost = cacheHost;
    }

    public Integer getCachePort() {
        return Objects.isNull(cachePort) ? 6379 : cachePort;
    }

    public void setCachePort(Integer cachePort) {
        this.cachePort = cachePort;
    }

    public String getCachePwd() {
        return cachePwd;
    }

    public void setCachePwd(String cachePwd) {
        this.cachePwd = cachePwd;
    }

    public Long getCacheTimeout() {
        return Objects.isNull(cacheTimeout) ? 5000 : cacheTimeout;
    }

    public void setCacheTimeout(Long cacheTimeout) {
        this.cacheTimeout = cacheTimeout;
    }

    public String getCacheMaster() {
        return cacheMaster;
    }

    public void setCacheMaster(String cacheMaster) {
        this.cacheMaster = cacheMaster;
    }

    public String getCacheNodes() {
        return cacheNodes;
    }

    public void setCacheNodes(String cacheNodes) {
        this.cacheNodes = cacheNodes;
    }

    public Integer getCacheMaxActive() {
        return Objects.isNull(cacheMaxActive) ? 10000 : cacheMaxActive;
    }

    public void setCacheMaxActive(Integer cacheMaxActive) {
        this.cacheMaxActive = cacheMaxActive;
    }

    public Integer getCacheMaxIdle() {
        return Objects.isNull(cacheMaxIdle) ? 50 : cacheMaxIdle;
    }

    public void setCacheMaxIdle(Integer cacheMaxIdle) {
        this.cacheMaxIdle = cacheMaxIdle;
    }

    public Integer getCacheMinIdle() {
        return Objects.isNull(cacheMinIdle) ? 0 : cacheMinIdle;
    }

    public void setCacheMinIdle(Integer cacheMinIdle) {
        this.cacheMinIdle = cacheMinIdle;
    }

    public Long getCacheMaxWait() {
        return Objects.isNull(cacheMaxWait) ? -1 : cacheMaxWait;
    }

    public void setCacheMaxWait(Long cacheMaxWait) {
        this.cacheMaxWait = cacheMaxWait;
    }

    /**
     * 线程池设置
     */
    class AsyncCacheProperties {
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
        private String threadNamePrefix = "sunny-cache-executor-";

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
}
