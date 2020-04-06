package com.sunny.maven.design;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/3/11 11:39
 * @description：代理模式
 *  结构：
 *      1、被代理对象，真实实例对象
 *      2、代理对象，内部调用真实实例对象
 *      3、外部调用都通过代理对象调用真实实例对象
 *      4、代理对象在调用真实实例对象的时候可以增加一些额外的操作
 *
 *  代理实现：
 *      1、JDK内部实现代理，又分为静态代理、动态代理
 *      2、第三方类库CGLIB实现代理
 * @modified By：
 * @version: 1.0.0
 */
public class ProxyDesign {
    public static void main(String ...args) {
        /**
         * JDK内部实现代理
         * 静态代理实现：
         *      1、接口类
         *      2、接口实现类
         *      3、代理类，实现接口，内部维护实现类实例，代理类方法内部调用实现类实例的方式实现逻辑，
         *      可以增加额外操作
         *      4、外部调用代理对象
         * 注：
         *      1、没有接口的类不是实现
         */
        IJDKProxy jdkInstance = new JDKProxyImpl();
        IJDKProxy jdkProxyStatic = new JDKStaticProxy(jdkInstance);
        jdkProxyStatic.helloWorld();
        /**
         * JDK内部实现代理
         * 动态代理实现：
         *      1、接口类
         *      2、接口实现类
         *      3、实现InvocationHandler接口的动态代理实现类，内部维护实现类实例，实现invoke方法，
         *      在方法内部通过Method类反射调用实现类实例的方法，invoke方法可以增加额外操作
         *      4、使用Proxy.newProxyInstance()方式升级代理对象，代理对象实现了接口类
         *      5、外部调用代理对象
         * 实现逻辑：
         *      1、使用反射实现
         *      2、代理对象是运行时动态生成
         *      3、生成方式是使用目标实现类的加载器和接口以及InvocationHandler接口实现类动态生成字节码文件，
         *      在使用加载器加载字节码文件进内存，通过反射生成代理实例
         * 注：
         *      1、没有接口的类不是实现
         */
        JDKDynamicProxy jdkDynamicProxy = new JDKDynamicProxy(jdkInstance);
        IJDKProxy jdkProxyDynamic = (IJDKProxy) jdkDynamicProxy.getProxyInstance();
        jdkProxyDynamic.helloWorld();
        /**
         * 根据接口生成代理类
         */
        JDKDynamicInterfaceProxy jdkDynamicInterfaceProxy = new JDKDynamicInterfaceProxy("hello");
        IJDKProxy jdkDynamicInterface = (IJDKProxy) jdkDynamicInterfaceProxy.getProxyInstance(IJDKProxy.class);
        jdkDynamicInterface.helloWorld();
        /**
         * CGLIB动态代理：JDK内部实现代理存在一个缺陷，即必须实现接口才能代理，因此出现了CGLIB动态代理
         * 动态代理实现：
         *      1、目标实现类
         *      2、实现MethodInterceptor接口的动态代理实现类，内部维护实现类实例，实现intercept方法，
         *      在方法内部通过Method类反射调用实现类实例的方法，intercept方法可以增加额外操作
         *      3、使用Enhancer操作字节码的方式生成代理对象，代理对象为目标对象的子类
         *      4、外部调用代理对象
         * 实现逻辑：
         *      1、基于字节码的方式实现
         *      2、代理对象是运行时动态生成
         *      3、生成的代理类为目标类的派生类
         * 注：
         *      1、final修饰符修复的类不能实现
         */
        CglibProxy cglibInstance = new CglibProxy();
        CglibDynamicProxy cglibDynamicProxy = new CglibDynamicProxy(cglibInstance);
        CglibProxy cglibProxy = (CglibProxy) cglibDynamicProxy.getProxyInstance();
        cglibProxy.helloWorld();
    }
    /**
     * JDK内部实现代理
     * 静态代理：
     */
    interface IJDKProxy {
        void helloWorld() ;
    }
    public static class JDKProxyImpl implements IJDKProxy {
        @Override
        public void helloWorld() {
            System.out.println("Hello World...");
        }
    }
    public static class JDKStaticProxy implements IJDKProxy {
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
     * JDK内部实现代理
     * 动态代理：
     */
    public static class JDKDynamicProxy implements InvocationHandler {
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
     * JDK动态代理，根据接口生成代理类
     */
    public static class JDKDynamicInterfaceProxy implements InvocationHandler {
        private String arg;
        public JDKDynamicInterfaceProxy(String arg) {
            this.arg = arg;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            System.out.println("jdk reflect interface begin ...");
            System.out.println("interface execute(" + arg + ") ...");
            System.out.println("jdk reflect interface begin ...");
            return null;
        }
        public Object getProxyInstance(Class clazz) {
            return Proxy.newProxyInstance(clazz.getClassLoader(), new Class<?>[] {clazz}, this);
        }
    }
    /**
     * CGLIB动态代理
     */
    public static class CglibProxy {
        public void helloWorld() {
            System.out.println("Hello World...");
        }
    }
    public static class CglibDynamicProxy implements MethodInterceptor {
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
}
