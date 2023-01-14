package com.sunny.maven.middle.scheduled;

import java.util.concurrent.ScheduledFuture;

/**
 * @author SUNNY
 * @description: ScheduledFuture的包装类
 * @create: 2022-11-11 18:23
 */
public class ScheduledTask {
    volatile ScheduledFuture<?> future;

    /**
     * 取消定时任务
     */
    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }
}
