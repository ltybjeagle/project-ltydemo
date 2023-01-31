package com.sunny.maven.rpc.reflect.javassist;

import com.sunny.maven.rpc.reflect.api.ReflectInvoker;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import javassist.util.proxy.ProxyFactory;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;

/**
 * @author SUNNY
 * @description: Javassist方式调用方法
 * @create: 2023-01-31 15:45
 */
@Slf4j
@SPIClass
public class JavassistReflectInvoker implements ReflectInvoker {
    @Override
    public Object invokeMethod(Object serviceBean, Class<?> serviceClass, String methodName,
                               Class<?>[] parameterTypes, Object[] parameters) throws Throwable {
        // javassist reflect
        log.info("use javassist reflect type invoke method ...");
        ProxyFactory proxyFactory = new ProxyFactory();
        proxyFactory.setSuperclass(serviceClass);
        Class<?> childClass = proxyFactory.createClass();
        Method method = childClass.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(childClass.newInstance(), parameters);
    }
}
