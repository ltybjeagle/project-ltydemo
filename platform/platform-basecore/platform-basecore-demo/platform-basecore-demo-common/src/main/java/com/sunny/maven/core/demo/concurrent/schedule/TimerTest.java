package com.sunny.maven.core.demo.concurrent.schedule;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author SUNNY
 * @description: 测试Timer
 * @create: 2023/7/17 14:49
 */
public class TimerTest {
    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println("测试Timer类");
            }
        }, 1000, 1000);
        Thread.sleep(10000);
        timer.cancel();
    }
}
