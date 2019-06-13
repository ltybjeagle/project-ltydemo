package com.liuty.maven.cache;

import lombok.extern.slf4j.Slf4j;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @描述:
 * @创建人: Sunny
 * @创建时间: 2019/5/30
 */
@Slf4j
public class CacheProxyObject implements MethodInterceptor {

    private Object target;

    public CacheProxyObject(Object target) {
        this.target = target;
    }

    public Object getProxyInstance() {
        Enhancer en = new Enhancer();
        en.setSuperclass(target.getClass());
        en.setCallback(this);
        return en.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        log.info("cglib proxy begin ...{}", method.getName());
        Object returnValue = method.invoke(target, args);
        log.info("cglib proxy end ...{}", method.getName());
        return returnValue;
    }
}
