package com.sunny.maven.rpc.proxy.cglib;

import com.sunny.maven.rpc.proxy.api.BaseProxyFactory;
import com.sunny.maven.rpc.proxy.api.ProxyFactory;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.InvocationHandler;

import java.lang.reflect.Method;

/**
 * @author SUNNY
 * @description: Cglib动态代理
 * @create: 2023-01-13 15:03
 */
@Slf4j
@SPIClass
public class CglibProxyFactory<T> extends BaseProxyFactory<T> implements ProxyFactory {
    private final Enhancer enhancer = new Enhancer();
    @Override
    public <T> T getProxy(Class<T> clazz) {
        log.info("基于CGLib动态代理...");
        enhancer.setInterfaces(new Class[] {clazz});
        enhancer.setCallback(new InvocationHandler() {
            @Override
            public Object invoke(Object o, Method method, Object[] objects) throws Throwable {
                return objectProxy.invoke(o, method, objects);
            }
        });
        return (T) enhancer.create();
    }
}
