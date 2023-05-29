package com.sunny.maven.core.demo.concurrent.executor.state;

import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: 线程不断休眠
 * @create: 2023/5/24 17:26
 */
public class WaitingTime implements Runnable {
    @Override
    public void run() {
        while (true) waitSecond(200);
    }

    /**
     * 线程等待多少秒
     * @param seconds 休眠时间（秒）
     */
    public static void waitSecond(long seconds) {
        try {
            TimeUnit.SECONDS.sleep(seconds);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
