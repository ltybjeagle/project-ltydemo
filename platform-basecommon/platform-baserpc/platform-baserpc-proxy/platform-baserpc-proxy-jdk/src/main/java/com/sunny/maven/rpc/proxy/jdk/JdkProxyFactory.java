package com.sunny.maven.rpc.proxy.jdk;

import com.sunny.maven.rpc.proxy.api.BaseProxyFactory;
import com.sunny.maven.rpc.proxy.api.ProxyFactory;

import java.lang.reflect.Proxy;

/**
 * @author SUNNY
 * @description: JDK动态代理
 * @create: 2023-01-01 21:49
 */
public class JdkProxyFactory<T> extends BaseProxyFactory<T> implements ProxyFactory {

    @Override
    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] {clazz}, objectProxy);
    }
}
