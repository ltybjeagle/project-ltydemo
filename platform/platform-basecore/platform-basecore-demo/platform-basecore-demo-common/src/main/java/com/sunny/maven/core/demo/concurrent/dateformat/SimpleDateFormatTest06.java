package com.sunny.maven.core.demo.concurrent.dateformat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * @author SUNNY
 * @description: 通过DateTimeFormatter类解决线程安全问题
 * 使用DateTimeFormatter类来处理日期的格式化操作运行效率比较高，推荐在高并发业务场景的生产环境使用
 * @create: 2023/7/13 13:45
 */
public class SimpleDateFormatTest06 {
    /**
     * 执行总次数
     */
    private static final int EXECUTE_COUNT = 1000;
    /**
     * 同时运行的线程数量
     */
    private static final int THREAD_COUNT = 20;
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static void main(String[] args) throws InterruptedException {
        final Semaphore semaphore = new Semaphore(THREAD_COUNT);
        final CountDownLatch downLatch = new CountDownLatch(EXECUTE_COUNT);
        ExecutorService executor = Executors.newCachedThreadPool();
        for (int i = 0; i < EXECUTE_COUNT; i++) {
            executor.submit(() -> {
                try {
                    semaphore.acquire();
                    try {
                        LocalDate.parse("2023-07-13", formatter);
                    } catch (Exception ex) {
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
