package com.sunny.maven.core.demo.concurrent.future;

import java.util.concurrent.*;

/**
 * @author SUNNY
 * @description: 测试Future获取异步结果
 * @create: 2023/7/12 9:47
 */
public class FutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<String> future = executorService.submit(() -> "测试Future获取异步结果");
        System.out.println(future.get());
        executorService.shutdown();
    }
}
