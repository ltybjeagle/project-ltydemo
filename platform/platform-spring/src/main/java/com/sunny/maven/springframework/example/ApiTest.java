package com.sunny.maven.springframework.example;

import com.sunny.maven.springframework.example.bean.UserService;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import net.bytebuddy.matcher.ElementMatchers;
import org.junit.Test;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.Callable;

/**
 * @author SUNNY
 * @description: 测试类
 * @create: 2023-02-25 22:40
 */
public class ApiTest {

    @Test
    public void test_aware_xml() throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
//        // 1.初始化 BeanFactory
//        ClassPathXmlApplicationContext applicationContext =
//                new ClassPathXmlApplicationContext("classpath:spring.xml");
//        applicationContext.registerShutdownHook();
//
//        // 2. 获取Bean对象调用方法
//        UserService userService = applicationContext.getBean("userService", UserService.class);
//        String result = userService.queryUserInfo();
//        System.out.println("测试结果：" + result);
//
//        System.out.println("ApplicationContextAware：" + userService.getApplicationContext());
//        System.out.println("BeanFactoryAware：" + userService.getBeanFactory());
//
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(UserService.class);
//        enhancer.setCallback(new NoOp() {
//            @Override
//            public int hashCode() {
//                return super.hashCode();
//            }
//        });
//        Object obj = enhancer.create(new Class[] {String.class}, new Object[] {"SUNNY"});
//        System.out.println(obj);

        Class<?> targetClass = MyService.class;

        Class<?> proxyClass = new ByteBuddy()
                .subclass(targetClass)
                .method(ElementMatchers.named("doSomething"))  // 拦截所有方法
                .intercept(MethodDelegation.to(MyInterceptor.class))  // 使用 MyInterceptor 进行方法拦截
                .make()
                .load(targetClass.getClassLoader())
                .getLoaded();

        // 创建代理实例
        MyService proxy = (MyService) proxyClass.getDeclaredConstructor().newInstance();

        // 调用代理方法
        proxy.doSomething();

    }

    public static class MyService {
        public void doSomething() {
            System.out.println("Doing something...");
        }
    }

    public static class MyInterceptor {
        public static void intercept(@SuperCall Callable<?> superCall) throws Exception {
            System.out.println("Before method invocation");
            superCall.call();
            System.out.println("After method invocation");
        }
    }

}
