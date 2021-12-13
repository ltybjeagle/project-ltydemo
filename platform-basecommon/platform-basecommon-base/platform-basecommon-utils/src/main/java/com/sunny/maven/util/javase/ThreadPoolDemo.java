package com.sunny.maven.util.javase;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: 线程池测试
 * @create: 2021-12-01 15:44
 */
public class ThreadPoolDemo {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(
                1, 2, 10, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(1),
                new ThreadPoolExecutor.DiscardPolicy());
        threadPool.allowCoreThreadTimeOut(true);
        System.out.println("1核心线程数" + threadPool.getCorePoolSize());
        threadPool.submit(() -> {
           System.out.println("开始" + System.currentTimeMillis());
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("结束" +  + System.currentTimeMillis());
        });
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("2核心线程数" + threadPool.getCorePoolSize());
        try {
            Thread.sleep(20000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("3核心线程数" + threadPool.getCorePoolSize());
    }
}
