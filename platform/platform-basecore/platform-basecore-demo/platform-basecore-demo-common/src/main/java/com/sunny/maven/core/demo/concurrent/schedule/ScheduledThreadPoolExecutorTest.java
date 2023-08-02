package com.sunny.maven.core.demo.concurrent.schedule;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: 测试ScheduledThreadPoolExecutor
 * @create: 2023/7/17 14:56
 */
public class ScheduledThreadPoolExecutorTest {
    public static void main(String[] args) throws InterruptedException {
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(3);
        executorService.scheduleAtFixedRate(() -> {
            System.out.println("测试测试ScheduledThreadPoolExecutor");
        }, 1, 1, TimeUnit.SECONDS);

        // 主线程休眠10秒
        Thread.sleep(10000);

        System.out.println("正在关闭线程池...");
        // 关闭线程池
        executorService.shutdown();

        boolean isClosed;
        // 等待线程池终止
        do {
            isClosed = executorService.awaitTermination(1, TimeUnit.DAYS);
            System.out.println("正在等待线程池中的任务执行完成");
        } while (!isClosed);

        System.out.println("所有线程执行结束，线程池关闭");
    }
}
