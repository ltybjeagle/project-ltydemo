package com.sunny.maven.rpc.reflect.bytebuddy;

import com.sunny.maven.rpc.reflect.api.ReflectInvoker;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.ByteBuddy;

import java.lang.reflect.Method;

/**
 * @author SUNNY
 * @description: ByteBuddy反射机制
 * @create: 2023-01-31 16:33
 */
@Slf4j
@SPIClass
public class ByteBuddyReflectInvoker implements ReflectInvoker {
    @Override
    public Object invokeMethod(Object serviceBean, Class<?> serviceClass, String methodName,
                               Class<?>[] parameterTypes, Object[] parameters) throws Throwable {
        // bytebuddy reflect
        log.info("use bytebuddy reflect type invoke method ...");
        Class<?> childClass = new ByteBuddy().subclass(serviceClass).
                make().
                load(ByteBuddyReflectInvoker.class.getClassLoader()).
                getLoaded();
        Object instance = childClass.getDeclaredConstructor().newInstance();
        Method method = childClass.getMethod(methodName, parameterTypes);
        method.setAccessible(true);
        return method.invoke(instance, parameters);
    }
}
