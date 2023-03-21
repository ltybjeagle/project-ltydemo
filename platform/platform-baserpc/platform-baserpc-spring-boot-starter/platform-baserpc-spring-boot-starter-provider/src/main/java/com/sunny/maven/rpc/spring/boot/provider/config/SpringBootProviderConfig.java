package com.sunny.maven.rpc.spring.boot.provider.config;

/**
 * @author SUNNY
 * @description: SpringBootProviderConfig
 * @create: 2023-02-16 13:35
 */
public final class SpringBootProviderConfig {

    /**
     * 服务地址
     */
    private String serverAddress;
    /**
     * 注册到注册中心的服务地址
     */
    private String serverRegistryAddress;
    /**
     * 注册中心地址
     */
    private String registryAddress;
    /**
     * 注册类型
     */
    private String registryType;
    /**
     * 负载均衡类型
     */
    private String registryLoadBalancerType;
    /**
     * 反射类型
     */
    private String reflectType;
    /**
     * 心跳时间间隔
     */
    private int heartbeatInterval;
    /**
     * 扫描并清理不活跃连接的时间间隔
     */
    private int scanNotActiveChannelInterval;
    /**
     * 是否开启结果缓存
     */
    private boolean enableResultCache;
    /**
     * 结果缓存的时长
     */
    private int resultCacheExpire;
    /**
     * 核心线程数
     */
    private int corePoolSize;
    /**
     * 最大线程数
     */
    private int maximumPoolSize;
    /**
     * 流控类型
     */
    private String flowType;
    /**
     * 最大连接限制
     */
    private int maxConnections;
    /**
     * 拒绝策略类型
     */
    private String disuseStrategyType;
    /**
     * 是否开启缓冲区
     */
    private boolean enableBuffer;
    /**
     * 缓冲区大小
     */
    private int bufferSize;
    /**
     * 是否开启限流
     */
    private boolean enableRateLimiter;
    /**
     * 限流类型
     */
    private String rateLimiterType;
    /**
     * 在milliSeconds毫秒内最多能够通过的请求个数
     */
    private int permits;
    /**
     * 毫秒数
     */
    private int milliSeconds;
    /**
     * 当限流失败时的处理策略
     */
    private String rateLimiterFailStrategy;
    /**
     * 是否开启熔断策略
     */
    private boolean enableFusing;
    /**
     * 熔断规则标识
     */
    private String fusingType;
    /**
     * 在fusingMilliSeconds毫秒内触发熔断操作的上限值
     */
    private double totalFailure;
    /**
     * 熔断的毫秒时长
     */
    private int fusingMilliSeconds;
    /**
     * 异常后置处理器标识
     */
    private String exceptionPostProcessorType;

    public SpringBootProviderConfig() {
    }

    public SpringBootProviderConfig(final String serverAddress, final String serverRegistryAddress,
                                    final String registryAddress, final String registryType,
                                    final String registryLoadBalancerType, final String reflectType,
                                    final int heartbeatInterval, final int scanNotActiveChannelInterval,
                                    final boolean enableResultCache, final int resultCacheExpire,
                                    final int corePoolSize, final int maximumPoolSize, final String flowType,
                                    final int maxConnections, final String disuseStrategyType,
                                    final boolean enableBuffer, final int bufferSize, final boolean enableRateLimiter,
                                    final String rateLimiterType, final int permits, final int milliSeconds,
                                    final String rateLimiterFailStrategy, final boolean enableFusing,
                                    final String fusingType, final double totalFailure, final int fusingMilliSeconds,
                                    final String exceptionPostProcessorType) {
        this.serverAddress = serverAddress;
        this.registryAddress = registryAddress;
        this.registryType = registryType;
        this.registryLoadBalancerType = registryLoadBalancerType;
        this.reflectType = reflectType;
        if (heartbeatInterval > 0) {
            this.heartbeatInterval = heartbeatInterval;
        }
        this.scanNotActiveChannelInterval = scanNotActiveChannelInterval;
        this.serverRegistryAddress = serverRegistryAddress;
        this.enableResultCache = enableResultCache;
        this.resultCacheExpire = resultCacheExpire;
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.flowType = flowType;
        this.maxConnections = maxConnections;
        this.disuseStrategyType = disuseStrategyType;
        this.enableBuffer = enableBuffer;
        this.bufferSize = bufferSize;
        this.enableRateLimiter = enableRateLimiter;
        this.rateLimiterType = rateLimiterType;
        this.permits = permits;
        this.milliSeconds = milliSeconds;
        this.rateLimiterFailStrategy = rateLimiterFailStrategy;
        this.enableFusing = enableFusing;
        this.fusingType = fusingType;
        this.totalFailure = totalFailure;
        this.fusingMilliSeconds = fusingMilliSeconds;
        this.exceptionPostProcessorType = exceptionPostProcessorType;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public String getRegistryAddress() {
        return registryAddress;
    }

    public void setRegistryAddress(String registryAddress) {
        this.registryAddress = registryAddress;
    }

    public String getRegistryType() {
        return registryType;
    }

    public void setRegistryType(String registryType) {
        this.registryType = registryType;
    }

    public String getRegistryLoadBalancerType() {
        return registryLoadBalancerType;
    }

    public void setRegistryLoadBalancerType(String registryLoadBalancerType) {
        this.registryLoadBalancerType = registryLoadBalancerType;
    }

    public String getReflectType() {
        return reflectType;
    }

    public void setReflectType(String reflectType) {
        this.reflectType = reflectType;
    }

    public int getHeartbeatInterval() {
        return heartbeatInterval;
    }

    public void setHeartbeatInterval(int heartbeatInterval) {
        this.heartbeatInterval = heartbeatInterval;
    }

    public int getScanNotActiveChannelInterval() {
        return scanNotActiveChannelInterval;
    }

    public void setScanNotActiveChannelInterval(int scanNotActiveChannelInterval) {
        this.scanNotActiveChannelInterval = scanNotActiveChannelInterval;
    }

    public String getServerRegistryAddress() {
        return serverRegistryAddress;
    }

    public void setServerRegistryAddress(String serverRegistryAddress) {
        this.serverRegistryAddress = serverRegistryAddress;
    }

    public boolean isEnableResultCache() {
        return enableResultCache;
    }

    public void setEnableResultCache(boolean enableResultCache) {
        this.enableResultCache = enableResultCache;
    }

    public int getResultCacheExpire() {
        return resultCacheExpire;
    }

    public void setResultCacheExpire(int resultCacheExpire) {
        this.resultCacheExpire = resultCacheExpire;
    }

    public int getCorePoolSize() {
        return corePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        this.corePoolSize = corePoolSize;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public void setMaximumPoolSize(int maximumPoolSize) {
        this.maximumPoolSize = maximumPoolSize;
    }

    public String getFlowType() {
        return flowType;
    }

    public void setFlowType(String flowType) {
        this.flowType = flowType;
    }

    public int getMaxConnections() {
        return maxConnections;
    }

    public void setMaxConnections(int maxConnections) {
        this.maxConnections = maxConnections;
    }

    public String getDisuseStrategyType() {
        return disuseStrategyType;
    }

    public void setDisuseStrategyType(String disuseStrategyType) {
        this.disuseStrategyType = disuseStrategyType;
    }

    public boolean isEnableBuffer() {
        return enableBuffer;
    }

    public void setEnableBuffer(boolean enableBuffer) {
        this.enableBuffer = enableBuffer;
    }

    public int getBufferSize() {
        return bufferSize;
    }

    public void setBufferSize(int bufferSize) {
        this.bufferSize = bufferSize;
    }

    public boolean isEnableRateLimiter() {
        return enableRateLimiter;
    }

    public void setEnableRateLimiter(boolean enableRateLimiter) {
        this.enableRateLimiter = enableRateLimiter;
    }

    public String getRateLimiterType() {
        return rateLimiterType;
    }

    public void setRateLimiterType(String rateLimiterType) {
        this.rateLimiterType = rateLimiterType;
    }

    public int getPermits() {
        return permits;
    }

    public void setPermits(int permits) {
        this.permits = permits;
    }

    public int getMilliSeconds() {
        return milliSeconds;
    }

    public void setMilliSeconds(int milliSeconds) {
        this.milliSeconds = milliSeconds;
    }

    public String getRateLimiterFailStrategy() {
        return rateLimiterFailStrategy;
    }

    public void setRateLimiterFailStrategy(String rateLimiterFailStrategy) {
        this.rateLimiterFailStrategy = rateLimiterFailStrategy;
    }

    public boolean isEnableFusing() {
        return enableFusing;
    }

    public void setEnableFusing(boolean enableFusing) {
        this.enableFusing = enableFusing;
    }

    public String getFusingType() {
        return fusingType;
    }

    public void setFusingType(String fusingType) {
        this.fusingType = fusingType;
    }

    public double getTotalFailure() {
        return totalFailure;
    }

    public void setTotalFailure(double totalFailure) {
        this.totalFailure = totalFailure;
    }

    public int getFusingMilliSeconds() {
        return fusingMilliSeconds;
    }

    public void setFusingMilliSeconds(int fusingMilliSeconds) {
        this.fusingMilliSeconds = fusingMilliSeconds;
    }

    public String getExceptionPostProcessorType() {
        return exceptionPostProcessorType;
    }

    public void setExceptionPostProcessorType(String exceptionPostProcessorType) {
        this.exceptionPostProcessorType = exceptionPostProcessorType;
    }
}
