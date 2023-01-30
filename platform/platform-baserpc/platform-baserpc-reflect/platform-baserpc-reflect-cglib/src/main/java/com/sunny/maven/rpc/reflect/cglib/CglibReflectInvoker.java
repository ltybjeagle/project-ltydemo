package com.sunny.maven.rpc.reflect.cglib;

import com.sunny.maven.rpc.reflect.api.ReflectInvoker;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.reflect.FastClass;
import net.sf.cglib.reflect.FastMethod;

/**
 * @author SUNNY
 * @description: Cglib 反射调用方法的类
 * @create: 2023-01-30 13:43
 */
@Slf4j
@SPIClass
public class CglibReflectInvoker implements ReflectInvoker {
    @Override
    public Object invokeMethod(Object serviceBean, Class<?> serviceClass, String methodName, Class<?>[] parameterTypes,
                               Object[] parameters) throws Throwable {
        // CGLib reflect
        log.info("use cglib reflect type invoke method ...");
        FastClass serviceFastClass = FastClass.create(serviceClass);
        FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);
        return serviceFastMethod.invoke(serviceBean, parameters);
    }
}
