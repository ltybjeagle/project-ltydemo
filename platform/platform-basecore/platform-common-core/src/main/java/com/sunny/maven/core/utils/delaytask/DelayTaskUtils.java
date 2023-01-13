package com.sunny.maven.core.utils.delaytask;

import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;
import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: 延迟任务
 * @create: 2022-08-03 17:37
 */
@Slf4j
public class DelayTaskUtils {
    /**
     * 存放定时任务
     */
    @Deprecated
    private static Map<String, Long> taskMap = new HashMap<>();

    public static void main(String[] args) {
        log.info("程序启动时间：{}", LocalDateTime.now());
        scheduledExecutorServiceTask();
    }

    /**
     * Netty 实现延迟任务
     */
    public static void nettyTask() {
        HashedWheelTimer timer = new HashedWheelTimer(3, TimeUnit.SECONDS, 100);
        TimerTask task = timeout -> log.info("执行任务：，执行时间：{}", LocalDateTime.now());
        timer.newTimeout(task, 0, TimeUnit.SECONDS);
    }

    /**
     * DelayQueue 实现延迟任务
     */
    @Deprecated
    public static void delayQueueTask() throws InterruptedException {
        DelayQueue<DelayElement> delayQueue = new DelayQueue<>();
        delayQueue.put(new DelayElement(1000));
        delayQueue.put(new DelayElement(3000));
        delayQueue.put(new DelayElement(5000));
        log.info("程序启动时间：{}", LocalDateTime.now());
        while(!delayQueue.isEmpty()) {
            log.info("执行任务：{}", delayQueue.take());
        }
        log.info("程序结束时间：{}", LocalDateTime.now());
    }

    /**
     * ScheduledExecutorService 实现固定频率一直循环执行任务
     */
    @Deprecated
    public static void scheduledExecutorServiceTask() {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        executorService.scheduleWithFixedDelay(() -> log.info("执行任务：，执行时间：{}", LocalDateTime.now()),
                2, 2, TimeUnit.SECONDS);
    }

    /**
     * 无限循环实现延迟任务
     */
    @Deprecated
    public static void loopTask() {
        Long itemLong = 0L;
        while (true) {
            for (Map.Entry<String, Long> entry : taskMap.entrySet()) {
                itemLong = entry.getValue();
                if (Instant.now().toEpochMilli() >= itemLong) {
                    log.info("执行任务：{}，执行时间：{}", entry.getKey(), LocalDateTime.now());
                    taskMap.remove(entry.getKey());
                }
            }
        }
    }

    /**
     * 添加任务
     * @param execTask 任务
     */
    @Deprecated
    public static void pushTask(String execTask) {
        taskMap.put(execTask, Instant.now().plusSeconds(3).toEpochMilli());
    }
}
