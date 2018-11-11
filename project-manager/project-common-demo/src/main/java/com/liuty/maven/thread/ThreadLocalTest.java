package com.liuty.maven.thread;

/**
 * Created by liutianyang on 2018/11/11.
 */
public class ThreadLocalTest {

    static void print(String str) {
        System.out.println(str + ":" + localVariable.get());
        localVariable.remove();
    }
    static ThreadLocal<String> localVariable = new ThreadLocal<>(); // 线程私有
    static ThreadLocal<String> threadLocal = new ThreadLocal<>();
    // 子线程可以获取父线程变量
    static ThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

    public static void main(String ...args) {
        Thread threadOne = new Thread(new Runnable() {
            @Override
            public void run() {
                localVariable.set("threadOne local variable");
                print("threadOne");
                System.out.println("threadOne remove after:" + localVariable.get());
            }
        });
        Thread threadTwo = new Thread(new Runnable() {
            @Override
            public void run() {
                localVariable.set("threadTwo local variable");
                print("threadOne");
                System.out.println("threadTwo remove after:" + localVariable.get());
            }
        });
        threadOne.start();
        threadTwo.start();

        threadLocal.set("hello world");
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("thread :" + threadLocal.get());
            }
        });
        thread.start();
        System.out.println("main :" + threadLocal.get());

        inheritableThreadLocal.set("inheritableThreadLocal hello world");
        Thread inheritableThread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("inheritable thread :" + inheritableThreadLocal.get());
            }
        });
        inheritableThread.start();
        System.out.println("inheritable main :" + inheritableThreadLocal.get());
    }
}
