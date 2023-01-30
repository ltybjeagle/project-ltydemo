package com.sunny.maven.rpc.proxy.asm;

import com.sunny.maven.rpc.proxy.api.BaseProxyFactory;
import com.sunny.maven.rpc.proxy.api.ProxyFactory;
import com.sunny.maven.rpc.proxy.asm.proxy.ASMProxy;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SUNNY
 * @description: ASM动态代理
 * @create: 2023-01-16 14:53
 */
@Slf4j
@SPIClass
public class ASMProxyFactory<T> extends BaseProxyFactory<T> implements ProxyFactory {
    @Override
    public <T> T getProxy(Class<T> clazz) {
        log.info("基于ASM动态代理...");
        try {
            return (T) ASMProxy.newProxyInstance(this.getClass().getClassLoader(), new Class[] {clazz}, objectProxy);
        } catch (Exception e) {
            log.error("asm proxy throws exception:{}", e.getMessage());
        }
        return null;
    }
}
