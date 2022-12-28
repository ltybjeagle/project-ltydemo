package com.sunny.maven.core.utils.delaytask;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.time.LocalDateTime;

/**
 * @author SUNNY
 * @description: 延迟任务测试类
 * @create: 2022-08-04 11:37
 */
@Slf4j
public class DelayTaskUtilsTest {

    @Test
    public void loopTaskTest() {
        log.info("程序启动时间：{}", LocalDateTime.now());
        DelayTaskUtils.pushTask("task-1");
        DelayTaskUtils.loopTask();
    }

    @Test
    public void scheduledExecutorServiceTaskTest() throws InterruptedException {
        log.info("程序启动时间：{}", LocalDateTime.now());
        DelayTaskUtils.scheduledExecutorServiceTask();
        /*
         * 开启新线程后，junit在主线程运行后会关闭，子线程也就无法运行了 (通过主线程休眠，让子线程能够执行)
         */
        Thread.sleep(100000);
    }

    @Test
    public void delayQueueTaskTest() throws InterruptedException {
        DelayTaskUtils.delayQueueTask();
    }

    @Test
    public void nettyTaskTest() throws InterruptedException {
        DelayTaskUtils.nettyTask();
        /*
         * 开启新线程后，junit在主线程运行后会关闭，子线程也就无法运行了 (通过主线程休眠，让子线程能够执行)
         */
        Thread.sleep(100000);
    }
}
