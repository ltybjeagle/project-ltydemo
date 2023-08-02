package com.sunny.maven.core.demo.concurrent.executor;

/**
 * @author SUNNY
 * @description: ThreadLocal使用示例
 * @create: 2023/7/18 14:39
 */
public class ThreadLocalTest {
    private static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    private static ThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();
    public static void main(String[] args) throws InterruptedException {
        // 创建第一个线程
        Thread threadA = new Thread(() -> {
            threadLocal.set("ThreadA：" + Thread.currentThread().getName());
            System.out.println("线程A本地变量中的值为：" + threadLocal.get());
            threadLocal.remove();
            System.out.println("线程A删除本地变量后ThreadLocal中的值为：" + threadLocal.get());
        });
        // 创建第二个线程
        Thread threadB = new Thread(() -> {
            threadLocal.set("ThreadB：" + Thread.currentThread().getName());
            System.out.println("线程B本地变量中的值为：" + threadLocal.get());
            System.out.println("线程B没有删除本地变量：" + threadLocal.get());
        });

        //启动线程A和线程B
        threadA.start();
        threadB.start();

        threadA.join();
        threadB.join();

        // 在主线程中设置值
        threadLocal.set("ThreadLocalTest");
        // 在子线程中获取值
        Thread thread = new Thread(() -> {
            System.out.println("子线程获取值：" + threadLocal.get());
        });

        // 启动子线程
        thread.start();
        // 在主线程中获取值
        System.out.println("主线程获取值：" + threadLocal.get());

        thread.join();
        System.out.println("InheritableThreadLocal实现让子线程访问到在父线程中设置的本地变量的值");

        // 在主线程中设置值
        inheritableThreadLocal.set("ThreadLocalTest");
        // 在子线程中获取值
        Thread threadC = new Thread(() -> {
            System.out.println("子线程获取值：" + inheritableThreadLocal.get());
        });

        // 启动子线程
        threadC.start();
        // 在主线程中获取值
        System.out.println("主线程获取值：" + inheritableThreadLocal.get());
    }
}
