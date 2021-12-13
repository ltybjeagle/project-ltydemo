package com.sunny.maven.common;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: 自定义日志类
 * @create: 2021-11-23 10:51
 */
@Component
public class MyLogger {
    @Async
    public void saveLog() {
        System.err.println(Thread.currentThread().getName());
    }
}
