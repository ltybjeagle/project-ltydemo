package com.sunny.maven.core.demo.concurrent.executor.sort;

/**
 * @author SUNNY
 * @description: 线程的顺序，直接调用Thread.start()方法执行不能确保线程的执行顺序
 * @create: 2023/7/7 12:32
 */
public class ThreadSort01 {

    public static void main(String[] args) {
        Thread thread1 = new Thread(() -> {
            System.out.println("thread1");
        });
        Thread thread2 = new Thread(() -> {
            System.out.println("thread2");
        });
        Thread thread3 = new Thread(() -> {
            System.out.println("thread3");
        });

        thread1.start();
        thread2.start();
        thread3.start();
    }
}
