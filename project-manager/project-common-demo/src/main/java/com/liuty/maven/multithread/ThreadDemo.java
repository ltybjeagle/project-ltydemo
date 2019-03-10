package com.liuty.maven.multithread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by liutianyang on 2018/11/10.
 * 并发：同一时间段内多个任务同时执行
 * 并行：单位时间内多个任务同时执行
 * JAVA内存模型：主内存、工作内存（CPU寄存器、一级缓存、二级缓存）、数据一致性
 * 多线程问题：原子性、可见性、指令重排序
 * JAVA线程与操作系统线程一一对应
 */
public class ThreadDemo {

    public static void main(String ...args) throws Exception {
        /*
         * 线程创建方式（3种）
         */
        // 第一种：继承Thread类实现
        MyThread myThread = new MyThread();
        myThread.start();
        // 第二种：实现Runnable接口实现
        MyRunnable task = new MyRunnable();
        new Thread(task).start();
        // 第三种：实现Callable接口实现，使用FutureTask异步任务类
        FutureTask<String> futureTask = new FutureTask<>(new MyCallable());
        new Thread(futureTask).start();
        try {
            // 阻塞获取结果
            String result = futureTask.get();
            System.out.println(result);
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*
         * join():阻塞等待调用线程执行终止
         * sleep(): 线程睡眠，释放CPU，获取锁的时候不释放锁
         */
        Thread joinThread = new Thread(() -> {
            try {
                // 线程睡眠1000ms
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        joinThread.start();
        // 阻塞等待joinThread结束后，主线程在执行
        try {
            joinThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*
         * yield(): 交出CPU时间片段，由线程调度器重新调度线程分配时间片（线程不挂起）
         * 可能刚释放CPU执行权后，就获得了执行权
         */
        new YieldThread();
        new YieldThread();
        new YieldThread();

        /*
         * interrupt() : 设置线程中断状态,如果线程处于wait()、sleep()、join()等挂起状态，
         * 将抛出InterruptedException异常
         * isInterrupted() ：检测当前线程是否中断，不清楚中断状态
         * interrupted() ：检测当前线程是否中断，清楚中断状态
         */
        Thread thread = new Thread(() -> {
            // 判断线程中断状态，不撤销状态
            while (!Thread.currentThread().isInterrupted()) {

            }
            System.out.println(Thread.currentThread() + " hello");
        });
        thread.start();
        Thread.sleep(1000);
        System.out.println("main thread interrupt thread");
        thread.interrupt();
        thread.join();
        System.out.println("main is over");

        Thread threadOne = new Thread(() -> {
            try {
                System.out.println("threadOne begin sleep for 2000 seconds");
                Thread.sleep(2000000); // 中断状态，抛出异常
                System.out.println("threadOne awaking");
            } catch (InterruptedException ex) {
                System.out.println("threadOne is interrupted while sleeping");
                return ;
            }
        });
        threadOne.start();
        Thread.sleep(1000);
        threadOne.interrupt();
        threadOne.join();
        System.out.println("main thread is over");

        /*
         * setDaemon() : 设置守护线程，如果进程内没有用户线程了（即使存在运行的守护线程），进程结束运行。
         * 线程分类：守护线程、用户线程
         */
        Thread daemonThread = new Thread(() -> {});
        daemonThread.setDaemon(true);
    }

    /**
     * 继承Thread ， 无返回值
     */
    public static class MyThread extends Thread {
        @Override
        public void run() {
            System.out.println("MyThread extends Thread");
        }
    }

    /**
     * 实现Runnable ， 无返回值
     */
    public static class MyRunnable implements Runnable {
        @Override
        public void run() {
            System.out.println("MyRunnable implements Runnable");
        }
    }

    /**
     * 实现Callable<T> ,有返回值
     */
    public static class MyCallable implements Callable<String> {
        @Override
        public String call() {
            return "MyCallable implements Callable";
        }
    }

    public static class YieldThread implements Runnable {
        public YieldThread() {
            Thread thread = new Thread(this);
            thread.start();
        }
        @Override
        public void run() {
            for (int i = 0; i < 5; i++) {
                if (i % 5 == 0) {
                    System.out.println(Thread.currentThread() + "Yield cpu ...");
                    // 释放CPU执行权
                    Thread.yield();
                }
            }
            System.out.println(Thread.currentThread() + "is Over");
        }
    }
}
