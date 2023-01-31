package com.sunny.maven.rpc.proxy.asm.proxy;

import com.sunny.maven.rpc.proxy.asm.classloader.ASMClassLoader;
import com.sunny.maven.rpc.proxy.asm.factory.ASMGenerateProxyFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SUNNY
 * @description: 自定义ASM代理
 * @create: 2023-01-16 14:21
 */
public class ASMProxy {

    protected InvocationHandler h;
    /**
     * 代理类名计数器
     */
    private static final AtomicInteger PROXY_CNT = new AtomicInteger(0);
    private static final String PROXY_CLASS_NAME_PRE = "$Proxy";

    public ASMProxy(InvocationHandler var1) {
        h = var1;
    }

    public static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)throws
            Exception {
        // 生成代理类Class
        Class<?> proxyClass = generate(interfaces);
        Constructor<?> constructor = proxyClass.getConstructor(InvocationHandler.class);
        return constructor.newInstance(h);
    }

    /**
      * 生成代理类Class
      *
      * @param interfaces
      * @return
      */
    private static Class<?> generate(Class<?>[] interfaces) throws ClassNotFoundException {
        String proxyClassName = PROXY_CLASS_NAME_PRE + PROXY_CNT.getAndIncrement();
        byte[] codes = ASMGenerateProxyFactory.generateClass(interfaces, proxyClassName);
        // 使用自定义类加载器加载字节码
        ASMClassLoader asmClassLoader = new ASMClassLoader();
        asmClassLoader.add(proxyClassName, codes);
        return asmClassLoader.loadClass(proxyClassName);
    }
}
