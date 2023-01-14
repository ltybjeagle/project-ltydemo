package com.sunny.maven.rpc.proxy.javassist;

import com.sunny.maven.rpc.proxy.api.BaseProxyFactory;
import com.sunny.maven.rpc.proxy.api.ProxyFactory;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import javassist.util.proxy.MethodHandler;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author SUNNY
 * @description: Javassist动态代理
 * @create: 2023-01-13 15:49
 */
@Slf4j
@SPIClass
public class JavassistProxyFactory<T> extends BaseProxyFactory<T> implements ProxyFactory {
    private javassist.util.proxy.ProxyFactory proxyFactory = new javassist.util.proxy.ProxyFactory();
    @Override
    public <T> T getProxy(Class<T> clazz) {
        log.info("基于Javassist动态代理...");
        try {
            // 设置代理类的父类
            proxyFactory.setInterfaces(new Class[] {clazz});
            proxyFactory.setHandler(new MethodHandler() {
                @Override
                public Object invoke(Object self, Method method, Method method1, Object[] objects) throws Throwable {
                    return objectProxy.invoke(self, method, objects);
                }
            });
            // 通过字节码技术动态创建子类实例
            return (T) proxyFactory.createClass().newInstance();
        } catch (Exception e) {
            log.error("javassist proxy throws exception:{}", e.getMessage());
        }
        return null;
    }
}
