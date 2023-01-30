package com.sunny.maven.rpc.proxy.bytebuddy;

import com.sunny.maven.rpc.proxy.api.BaseProxyFactory;
import com.sunny.maven.rpc.proxy.api.ProxyFactory;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;

/**
 * @author SUNNY
 * @description: ByteBuddy动态代理
 * @create: 2023-01-15 15:28
 */
@Slf4j
@SPIClass
public class ByteBuddyProxyFactory<T> extends BaseProxyFactory<T> implements ProxyFactory {
    @Override
    public <T> T getProxy(Class<T> clazz) {
        log.info("基于ByteBuddy动态代理...");
        try {
            return (T) new ByteBuddy().subclass(Object.class).
                    implement(clazz).
                    intercept(InvocationHandlerAdapter.of(objectProxy)).
                    make().
                    load(ByteBuddyProxyFactory.class.getClassLoader()).
                    getLoaded().
                    getDeclaredConstructor().
                    newInstance();
        } catch (Exception e) {
            log.error("bytebuddy proxy throws exception:{}", e.getMessage());
        }
        return null;
    }
}
