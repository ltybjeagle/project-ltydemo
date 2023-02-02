package com.sunny.maven.rpc.reflect.asm;

import com.sunny.maven.rpc.reflect.api.ReflectInvoker;
import com.sunny.maven.rpc.reflect.asm.proxy.ReflectProxy;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author SUNNY
 * @description: ASM反射机制
 * @create: 2023-02-01 09:57
 */
@Slf4j
@SPIClass
public class AsmReflectInvoker implements ReflectInvoker {
    @Override
    public Object invokeMethod(Object serviceBean, Class<?> serviceClass, String methodName,
                               Class<?>[] parameterTypes, Object[] parameters) throws Throwable {
        // Asm reflect
        log.info("use Asm reflect type invoke method ...");
        Constructor<?> constructor = serviceClass.getConstructor(new Class[]{});
        Object[] constructorParam = new Object[]{};
        Object instance = ReflectProxy.newProxyInstance(AsmReflectInvoker.class.getClassLoader(),
                getInvocationHandler(serviceBean), serviceClass, constructor, constructorParam);
        Method method = serviceClass.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(instance, parameters);
    }

    private InvocationHandler getInvocationHandler(Object obj) {
        return (proxy, method, args) -> {
            log.info("use proxy invoke method ...");
            method.setAccessible(true);
            Object result = method.invoke(obj, args);
            return result;
        };
    }
}
