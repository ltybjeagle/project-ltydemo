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

    public SpringBootProviderConfig() {
    }

    public SpringBootProviderConfig(final String serverAddress, final String serverRegistryAddress,
                                    final String registryAddress, final String registryType,
                                    final String registryLoadBalancerType, final String reflectType,
                                    final int heartbeatInterval, final int scanNotActiveChannelInterval,
                                    final boolean enableResultCache, final int resultCacheExpire,
                                    final int corePoolSize, final int maximumPoolSize, final String flowType,
                                    final int maxConnections, final String disuseStrategyType) {
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
}
