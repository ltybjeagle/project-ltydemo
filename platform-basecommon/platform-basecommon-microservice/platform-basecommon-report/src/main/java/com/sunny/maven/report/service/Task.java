package com.sunny.maven.report.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.concurrent.Future;

/**
 * @author SUNNY
 * @description: 任务执行
 * @create: 2022-09-04 12:05
 */
@Slf4j
@Component
public class Task {
    public static Random random = new Random();

    @Async
    public Future<String> doTaskOne() throws Exception {
        log.info("开始做任务一");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("完成任务一，耗时：{}毫秒", (end - start));
        return new AsyncResult<>("任务一完成");
    }

    @Async
    public Future<String> doTaskTwo() throws Exception {
        log.info("开始做任务二");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("完成任务二，耗时：{}毫秒", (end - start));
        return new AsyncResult<>("任务二完成");
    }

    @Async
    public Future<String> doTaskThree() throws Exception {
        log.info("开始做任务三");
        long start = System.currentTimeMillis();
        Thread.sleep(random.nextInt(10000));
        long end = System.currentTimeMillis();
        log.info("完成任务三，耗时：{}毫秒", (end - start));
        return new AsyncResult<>("任务三完成");
    }
}
