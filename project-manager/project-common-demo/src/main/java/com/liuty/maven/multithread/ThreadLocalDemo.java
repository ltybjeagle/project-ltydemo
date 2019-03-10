package com.liuty.maven.multithread;

/**
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
        /*
         * ThreadLocal : 线程私有变量，每个线程保存变量的一个副本，使用时线程使用自己的副本变量
         * 子线程不能访问父线程的变量副本
         * Thread里有threadLocals变量（MAP），存储变量副本（key:ThreadLocal变量值，value：副本值）
         * ThreadLocal通过当前线程回去线程的threadLocals变量，获取变量副本
         */
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
