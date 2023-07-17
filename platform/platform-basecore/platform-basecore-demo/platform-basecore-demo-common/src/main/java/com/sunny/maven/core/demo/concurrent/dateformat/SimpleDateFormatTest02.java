package com.sunny.maven.core.demo.concurrent.dateformat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author SUNNY
 * @description: 通过Synchronized锁解决SimpleDateFormat类的线程安全问题
 * 在程序的执行过程中，为SimpleDateFormat类对象加上了synchronized锁，导致同一时刻只能有一个线程执行parse(String)方法。
 * 此时，会影响程序的执行性能，在要求高并发的生产环境下，此种方式也是不太推荐使用的。
 * @create: 2023/7/13 11:40
 */
public class SimpleDateFormatTest02 {

    /**
     * 执行总次数
     */
    private static final int EXECUTE_COUNT = 1000;
    /**
     * 同时运行的线程数量
     */
    private static final int THREAD_COUNT = 20;
    /**
     * SimpleDateFormat对象
     */
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(THREAD_COUNT);
        final CountDownLatch downLatch = new CountDownLatch(EXECUTE_COUNT);
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < EXECUTE_COUNT; i++) {
            executor.submit(() -> {
                try {
                    semaphore.acquire();
                    try {
                        synchronized (simpleDateFormat) {
                            simpleDateFormat.parse("2023-07-13");
                        }
                    } catch (ParseException | NumberFormatException ex) {
                        System.out.println("线程：" + Thread.currentThread().getName() +
                                " 格式化日期失败");
                        ex.printStackTrace();
                        System.exit(1);
                    }
                    semaphore.release();
                } catch (InterruptedException e) {
                    System.out.println("信号量发生错误");
                    e.printStackTrace();
                    System.exit(1);
                }
                downLatch.countDown();
            });
        }
        downLatch.await();
        executor.shutdown();
        System.out.println("所有线程格式化日期成功");
    }
}
