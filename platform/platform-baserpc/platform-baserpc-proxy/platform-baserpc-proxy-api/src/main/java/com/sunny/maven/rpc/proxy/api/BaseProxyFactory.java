package com.sunny.maven.rpc.proxy.api;

import com.sunny.maven.rpc.proxy.api.config.ProxyConfig;
import com.sunny.maven.rpc.proxy.api.object.ObjectProxy;

/**
 * @author SUNNY
 * @description: 基础代理工厂类
 * @create: 2023-01-03 13:59
 */
public abstract class BaseProxyFactory<T> implements ProxyFactory {
    protected ObjectProxy<T> objectProxy;

    @Override
    public <T> void init(ProxyConfig<T> proxyConfig) {
        this.objectProxy = new ObjectProxy(proxyConfig.getClazz(), proxyConfig.getServiceVersion(),
                proxyConfig.getServiceGroup(), proxyConfig.getTimeout(), proxyConfig.getConsumer(),
                proxyConfig.getSerializationType(), proxyConfig.isAsync(), proxyConfig.isOneWay(),
                proxyConfig.getRegistryService(), proxyConfig.isEnableResultCache(),
                proxyConfig.getResultCacheExpire(), proxyConfig.getReflectType(), proxyConfig.getFallbackClassName(),
                proxyConfig.isEnableRateLimiter(), proxyConfig.getRateLimiterType(), proxyConfig.getPermits(),
                proxyConfig.getMilliSeconds(), proxyConfig.getFallbackClass());
    }
}
