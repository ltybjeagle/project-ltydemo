package com.liuty.maven.design;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 代理模式
 */
public class ProxyDesign {
    public static void main(String ...args) {
        // JDK代理
        IJDKProxy jdkInstance = new JDKProxyImpl();
        // JDK静态代理
        IJDKProxy jdkProxyStatic = new JDKStaticProxy(jdkInstance);
        jdkProxyStatic.helloWorld();
        // JDK动态代理
        JDKDynamicProxy jdkDynamicProxy = new JDKDynamicProxy(jdkInstance);
        IJDKProxy jdkProxyDynamic = (IJDKProxy) jdkDynamicProxy.getProxyInstance();
        jdkProxyDynamic.helloWorld();
        // CGLIB动态代理
        CglibProxy cglibInstance = new CglibProxy();
        CglibDynamicProxy cglibDynamicProxy = new CglibDynamicProxy(cglibInstance);
        CglibProxy cglibProxy = (CglibProxy) cglibDynamicProxy.getProxyInstance();
        cglibProxy.helloWorld();
    }
}
/**
 * JDK代理
 */
interface IJDKProxy {
    void helloWorld() ;
}
class JDKProxyImpl implements IJDKProxy {
    @Override
    public void helloWorld() {
        System.out.println("Hello World...");
    }
}
/**
 * JDK静态代理实现类
 */
class JDKStaticProxy implements IJDKProxy {
    private IJDKProxy ijdkProxy;
    public JDKStaticProxy(IJDKProxy ijdkProxy) {
        this.ijdkProxy = ijdkProxy;
    }
    @Override
    public void helloWorld() {
        System.out.println("static begin ...");
        ijdkProxy.helloWorld();
        System.out.println("static end ...");
    }
}
/**
 * JDK动态代理实现类（反射）
 */
class JDKDynamicProxy implements InvocationHandler {
    private Object target;
    public JDKDynamicProxy(Object target) {
        this.target = target;
    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("jdk reflect begin ...");
        Object returnValue = method.invoke(target, args);
        System.out.println("jdk reflect end ...");
        return returnValue;
    }
    public Object getProxyInstance() {
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(), this);
    }
}
/**
 * CGLIB动态代理
 */
class CglibProxy {
    public void helloWorld() {
        System.out.println("Hello World...");
    }
}
/**
 * CGLIB动态代理实现类（基于字节码）
 */
class CglibDynamicProxy implements MethodInterceptor {
    private Object target;
    public CglibDynamicProxy(Object target) {
        this.target = target;
    }
    public Object getProxyInstance() {
        Enhancer en = new Enhancer();
        en.setSuperclass(target.getClass());
        en.setCallback(this);
        return en.create();
    }
    @Override
    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy)
            throws Throwable {
        System.out.println("cglib proxy begin ...");
        Object returnValue = method.invoke(target, args);
        System.out.println("cglib proxy end ...");
        return returnValue;
    }
}