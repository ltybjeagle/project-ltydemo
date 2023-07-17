package com.sunny.maven.core.demo.concurrent.dateformat;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author SUNNY
 * @description: 通过ThreadLocal解决SimpleDateFormat类的线程安全问题（方式二）
 * 此种方式运行效率比较高，推荐在高并发业务场景的生产环境使用
 * @create: 2023/7/13 13:41
 */
public class SimpleDateFormatTest05 {
    /**
     * 执行总次数
     */
    private static final int EXECUTE_COUNT = 1000;
    /**
     * 同时运行的线程数量
     */
    private static final int THREAD_COUNT = 20;

    private static ThreadLocal<DateFormat> threadLocal = new ThreadLocal<>();

    private static DateFormat getDateFormat() {
        DateFormat dateFormat = threadLocal.get();
        if (dateFormat == null) {
            dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            threadLocal.set(dateFormat);
        }
        return dateFormat;
    }

    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(THREAD_COUNT);
        final CountDownLatch downLatch = new CountDownLatch(EXECUTE_COUNT);
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < EXECUTE_COUNT; i++) {
            executor.submit(() -> {
                try {
                    semaphore.acquire();
                    try {
                        getDateFormat().parse("2023-07-13");
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
