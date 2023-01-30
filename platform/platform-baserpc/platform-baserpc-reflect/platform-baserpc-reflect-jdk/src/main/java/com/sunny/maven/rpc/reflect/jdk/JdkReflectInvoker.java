package com.sunny.maven.rpc.reflect.jdk;

import com.sunny.maven.rpc.reflect.api.ReflectInvoker;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author SUNNY
 * @description: JDK反射调用方法的类
 * @create: 2023-01-29 17:26
 */
@Slf4j
@SPIClass
public class JdkReflectInvoker implements ReflectInvoker {
    @Override
    public Object invokeMethod(Object serviceBean, Class<?> serviceClass, String methodName, Class<?>[] parameterTypes,
                               Object[] parameters) throws Throwable {
        // JDK reflect
        log.info("use jdk reflect type invoke method ...");
        Method method = serviceClass.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(serviceBean, parameters);
    }
}
