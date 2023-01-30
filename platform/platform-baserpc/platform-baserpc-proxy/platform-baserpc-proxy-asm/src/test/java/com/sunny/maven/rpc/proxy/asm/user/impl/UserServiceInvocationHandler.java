package com.sunny.maven.rpc.proxy.asm.user.impl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author SUNNY
 * @description: InvocationHandler
 * @create: 2023-01-17 09:39
 */
public class UserServiceInvocationHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("invoke:" + proxy.getClass().getSimpleName() + "." + method.getName() + ":" +
                (System.currentTimeMillis() - start) + "ms");
        return "admin".equals(args[0]) && "admin".equals(args[1]);
    }
}
