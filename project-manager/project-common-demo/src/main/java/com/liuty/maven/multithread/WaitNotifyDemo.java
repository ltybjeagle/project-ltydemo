package com.liuty.maven.multithread;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * 线程通知等待
 */
public class WaitNotifyDemo {

    public static int MAX_SIZE = 10;

    public static void main(String ...args) {
        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<>(MAX_SIZE);

        /*
         * lambda表达式写法，接口runnable实现线程逻辑
         * 等待函数 :
         * wait() : 同一个监视器对象的通知函数、中断函数能够唤醒
         * wait(timeout) : 同一个监视器对象的通知函数、中断函数、超时时间能够唤醒
         * 通知函数 :
         * notify() : 唤醒随机一个线程
         * notifyAll() : 唤醒所有阻塞队列上的线程
         * 中断函数 :
         * interrupt() : 设置线程中断状态
         * 虚假唤醒 : 不通过上面三个方法唤醒了阻塞的线程，需要while条件判断是否满足唤醒条件，
         * 不满足需要再次阻塞
         */
        Thread threadA = new Thread(() -> {
            synchronized (queue) {
                // 条件判断是否满足唤醒条件
                while (queue.size() == MAX_SIZE) {
                    try {
                        queue.wait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                queue.add("String");
                queue.notifyAll();
            }
        });

        Thread threadB = new Thread(() -> {
            synchronized (queue) {
                while (queue.size() == 0) {
                    try {
                        queue.wait();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                try {
                    queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                queue.notifyAll();
            }
        });

        threadA.start();
        threadB.start();
    }
}
