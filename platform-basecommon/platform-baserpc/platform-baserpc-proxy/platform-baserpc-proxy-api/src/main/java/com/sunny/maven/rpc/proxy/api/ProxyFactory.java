package com.sunny.maven.rpc.proxy.api;

import com.sunny.maven.rpc.proxy.api.config.ProxyConfig;

/**
 * @author SUNNY
 * @description: 代理工厂接口
 * @create: 2023-01-03 13:56
 */
public interface ProxyFactory {

    /**
     * 获取代理对象
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T getProxy(Class<T> clazz);

    /**
     * 默认初始化方法
     * @param proxyConfig
     * @param <T>
     */
    default <T> void init(ProxyConfig<T> proxyConfig) {}
}
