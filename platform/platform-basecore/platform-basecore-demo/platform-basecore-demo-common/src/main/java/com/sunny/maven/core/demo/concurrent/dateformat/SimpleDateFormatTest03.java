package com.sunny.maven.core.demo.concurrent.dateformat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author SUNNY
 * @description: 通过Lock锁解决SimpleDateFormat类的线程安全问题
 * 此种方式同样会影响高并发场景下的性能，不太建议在高并发的生产环境使用
 * @create: 2023/7/13 12:16
 */
public class SimpleDateFormatTest03 {
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
    /**
     * Lock对象
     */
    private static Lock lock = new ReentrantLock();
    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(THREAD_COUNT);
        final CountDownLatch downLatch = new CountDownLatch(EXECUTE_COUNT);
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < EXECUTE_COUNT; i++) {
            executor.submit(() -> {
                try {
                    semaphore.acquire();
                    try {
                        lock.lock();
                        simpleDateFormat.parse("2023-07-13");
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
                } finally {
                    lock.unlock();
                }
                downLatch.countDown();
            });
        }
        downLatch.await();
        executor.shutdown();
        System.out.println("所有线程格式化日期成功");
    }
}
