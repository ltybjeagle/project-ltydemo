package com.sunny.maven.rpc.proxy.jdk;

import com.sunny.maven.rpc.proxy.api.consumer.Consumer;
import com.sunny.maven.rpc.proxy.api.object.ObjectProxy;

import java.lang.reflect.Proxy;

/**
 * @author SUNNY
 * @description: JDK动态代理
 * @create: 2023-01-01 21:49
 */
public class JdkProxyFactory<T> {
    /**
     * 服务版本号
     */
    private String serviceVersion;
    /**
     * 服务分组
     */
    private String serviceGroup;
    /**
     * 超时时间，默认15s
     */
    private long timeout = 15000;
    /**
     * 服务消费者
     */
    private Consumer consumer;
    /**
     * 序列化类型
     */
    private String serializationType;
    /**
     * 是否异步调用
     */
    private boolean async;
    /**
     * 是否单向调用
     */
    private boolean oneWay;

    public JdkProxyFactory(String serviceVersion, String serviceGroup, long timeout, Consumer consumer,
                           String serializationType, boolean async, boolean oneWay) {
        this.serviceVersion = serviceVersion;
        this.serviceGroup = serviceGroup;
        this.timeout = timeout;
        this.consumer = consumer;
        this.serializationType = serializationType;
        this.async = async;
        this.oneWay = oneWay;
    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] {clazz},
                new ObjectProxy<T>(clazz, serviceVersion, serviceGroup, timeout, consumer, serializationType,
                        async, oneWay));
    }
}
