package com.sunny.maven.core.utils.futuretest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.*;

/**
 * @author SUNNY
 * @description: Future测试
 * @create: 2022-08-26 11:43
 */
@Slf4j
public class FutureTest {

    @Test
    public void futureTest() throws InterruptedException, ExecutionException {
        // 第一种方式:Future + ExecutorService
        Task task = new Task();
        ExecutorService service = Executors.newCachedThreadPool();
        Future<Integer> future = service.submit(task);
        Integer integer1 = future.get();
        log.info("Future + ExecutorService，Result = {}", integer1);

        // 第二种方式: FutureTask + ExecutorService
        FutureTask<Integer> futureTask = new FutureTask<>(task);
        service.submit(futureTask);
        Integer integer2 = futureTask.get();
        log.info("FutureTask + ExecutorService，Result = {}", integer2);
        service.shutdown();

        // 第三种方式:FutureTask + Thread
        FutureTask<Integer> futureTask1 = new FutureTask<>(new Task());
        Thread thread = new Thread(futureTask1);
        thread.setName("Task thread");
        thread.start();
        Thread.sleep(1000);
        log.info("Thread [{}] is running", Thread.currentThread().getName());
        if (!futureTask1.isDone()) {
            log.info("Task is not done");
            Thread.sleep(2000);
        }
        int result = futureTask1.get();
        log.info("FutureTask + Thread，Result = {}", result);
        Thread.sleep(100000);
    }

    static class Task implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            log.info("Thread [{}] is running", Thread.currentThread().getName());
            int result = 0;
            for (int i = 0; i < 100; i++) {
                result += i;
            }

            Thread.sleep(3000);
            return result;
        }
    }
}
