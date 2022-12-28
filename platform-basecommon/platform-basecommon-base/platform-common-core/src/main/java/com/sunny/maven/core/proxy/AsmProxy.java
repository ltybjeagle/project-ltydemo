package com.sunny.maven.core.proxy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationHandler;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author SUNNY
 * @description: ASMProxy
 * @create: 2022-11-13 18:46
 */
public class AsmProxy {
    protected InvocationHandler h;
    /**
     * 代理类名计数器
     */
    private static final AtomicInteger PROXY_CNT = new AtomicInteger(0);
    private static final String PROXY_CLASS_NAME_PRE = "$Proxy";

    public AsmProxy(InvocationHandler var1) {
        h = var1;
    }

    public static Object newProxyInstance(ClassLoader loader, Class<?>[] interfaces, InvocationHandler h)
            throws Exception {
        // 生成代理类Class
        Class<?> proxyClass = generate(interfaces);
        Constructor<?> constructor = proxyClass.getConstructor(InvocationHandler.class);
        return constructor.newInstance(h);
    }

    /**
     * 生成代理类Class
     * @param interfaces
     * @return
     */
    private static Class<?> generate(Class<?>[] interfaces) throws ClassNotFoundException {
        String proxyClassName = PROXY_CLASS_NAME_PRE + PROXY_CNT.getAndIncrement();
        byte[] codes = AsmProxyFactory.generateClass(interfaces, proxyClassName);
        // 使用自定义类加载器加载字节码
        AsmClassLoader asmClassLoader = new AsmClassLoader();
        asmClassLoader.add(proxyClassName, codes);
        return asmClassLoader.loadClass(proxyClassName);
    }
}
