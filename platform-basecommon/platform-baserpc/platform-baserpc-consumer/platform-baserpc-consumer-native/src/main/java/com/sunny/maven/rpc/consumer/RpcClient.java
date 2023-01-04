package com.sunny.maven.rpc.consumer;

import com.sunny.maven.rpc.consumer.common.RpcConsumer;
import com.sunny.maven.rpc.proxy.api.ProxyFactory;
import com.sunny.maven.rpc.proxy.api.async.IAsyncObjectProxy;
import com.sunny.maven.rpc.proxy.api.config.ProxyConfig;
import com.sunny.maven.rpc.proxy.api.object.ObjectProxy;
import com.sunny.maven.rpc.proxy.jdk.JdkProxyFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SUNNY
 * @description: 服务消费客户端
 * @create: 2023-01-01 22:34
 */
@Slf4j
public class RpcClient {
    /**
     * 服务版本号
     */
    private String serviceVersion;
    /**
     * 服务分组
     */
    private String serviceGroup;
    /**
     * 序列化类型
     */
    private String serializationType;
    /**
     * 超时时间，默认15s
     */
    private long timeout;
    /**
     * 是否异步调用
     */
    private boolean async;
    /**
     * 是否单向调用
     */
    private boolean oneWay;

    public RpcClient(String serviceVersion, String serviceGroup, String serializationType, long timeout,
                           boolean async, boolean oneWay) {
        this.serviceVersion = serviceVersion;
        this.serviceGroup = serviceGroup;
        this.serializationType = serializationType;
        this.timeout = timeout;
        this.async = async;
        this.oneWay = oneWay;
    }

    public <T> T create(Class<T> interfaceClass) {
        ProxyFactory proxyFactory = new JdkProxyFactory<T>();
        proxyFactory.init(new ProxyConfig(interfaceClass, serviceVersion, serviceGroup, timeout,
                RpcConsumer.getInstance(), serializationType, async, oneWay));
        return proxyFactory.getProxy(interfaceClass);
    }

    public <T> IAsyncObjectProxy createAsync(Class<T> interfaceClass) {
        return new ObjectProxy<T>(interfaceClass, serviceVersion, serviceGroup, timeout, RpcConsumer.getInstance(),
                serializationType, async, oneWay);
    }

    public void shutdown() {
        RpcConsumer.getInstance().close();
    }
}