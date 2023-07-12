package com.sunny.maven.core.demo.concurrent.future;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * @author SUNNY
 * @description: 测试FutureTask获取异步结果
 * @create: 2023/7/12 10:23
 */
public class FutureTaskTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 线程测试
        FutureTask<String> futureTask = new FutureTask<>(() -> "测试FutureTask获取异步结果");
        new Thread(futureTask).start();
        System.out.println("线程执行：" + futureTask.get());

        // 线程池测试
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(futureTask);
        System.out.println("线程池执行：" + futureTask.get());
        executorService.shutdown();
    }
}
