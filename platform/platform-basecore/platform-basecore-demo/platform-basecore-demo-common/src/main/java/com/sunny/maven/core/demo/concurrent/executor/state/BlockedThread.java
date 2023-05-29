package com.sunny.maven.core.demo.concurrent.executor.state;

import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: 加锁后不再释放锁
 * @create: 2023/5/24 18:21
 */
public class BlockedThread implements Runnable {
    @Override
    public void run() {
        synchronized (BlockedThread.class) {
            while (true) {
                WaitingTime.waitSecond(100);
            }
        }
    }
}
