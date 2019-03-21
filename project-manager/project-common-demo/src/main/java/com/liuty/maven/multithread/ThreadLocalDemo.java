package com.liuty.maven.multithread;

/**
 * ThreadLocal为变量在每个线程中都创建一个副本，所以每个线程都可以访问自己内部的副本变量，
 * 不同线程之间不会相互干扰。
 *      1、ThreadLocal是变量在不同线程间的隔离性，子线程不能访问父线程的变量副本。
 *      2、每个线程内部都包含一个对ThreadLocalMap实例的引用threadLocals（MAP）变量，
 *      存储变量副本（key:ThreadLocal变量值，value：副本值）。
 *      3、ThreadLocal通过当前线程会去线程的threadLocals变量，获取变量副本
 *
 * ThreadLocalMap实例相当于线程的局部变量空间，存储着线程各自的数据。通过ThreadLocalMap可以获取entry数组，
 * 数组里存储的线程的变量数据。
 *      1、size：数组元素的数量。
 *      2、threshold：数组阀值、临界值（一般为长度的2/3）。
 *          size >= threshold时遍历数组，删除KEY为null的元素
 *          size >= threshold*3/4时需要对数据进行扩容，一般扩容是在原数组长度乘以2。
 *
 * InheritableThreadLocal类：允许子线程可以从父线程中获取值。
 *
 * 内存泄露问题：ThreadLocal的key是弱引用，value是强引用。
 *
 * Created by liutianyang on 2018/11/11.
 */
public class ThreadLocalDemo {

    static void print(String str) {
        System.out.println(str + ":" + localVariable.get());
        localVariable.remove();
    }
    // 线程私有
    static ThreadLocal<String> localVariable = new ThreadLocal<>();
    static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    // 子线程可以获取父线程变量
    static ThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

    public static void main(String ...args) {
        Thread threadOne = new Thread(() -> {
            localVariable.set("threadOne local variable");
            print("threadOne");
            System.out.println("threadOne remove after:" + localVariable.get());
        });
        Thread threadTwo = new Thread(() -> {
            localVariable.set("threadTwo local variable");
            print("threadOne");
            System.out.println("threadTwo remove after:" + localVariable.get());
        });
        threadOne.start();
        threadTwo.start();
        /*
         * 子线程不能访问父线程的变量副本
         */
        threadLocal.set("hello world");
        Thread thread = new Thread(() -> System.out.println("thread :" + threadLocal.get()));
        thread.start();
        System.out.println("main :" + threadLocal.get());
        /*
         * InheritableThreadLocal : 子线程可以访问父线程的变量副本
         */
        inheritableThreadLocal.set("inheritableThreadLocal hello world");
        Thread inheritableThread = new Thread(
                () -> System.out.println("inheritable thread :" + inheritableThreadLocal.get())
        );
        inheritableThread.start();
        System.out.println("inheritable main :" + inheritableThreadLocal.get());
    }
}
