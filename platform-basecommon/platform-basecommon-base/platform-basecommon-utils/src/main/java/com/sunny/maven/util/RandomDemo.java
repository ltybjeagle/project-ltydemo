package com.sunny.maven.util;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author SUNNY
 * @description: 随机数工具集
 * @create: 2021-07-24 16:18
 */
public class RandomDemo {
    public static void main(String ...args) {
        RandomDemo randomDemo = new RandomDemo();
        randomDemo.threadLocalRandomOption();
    }

    /**
     * ThreadLocalRandom 工具
     */
    public void threadLocalRandomOption() {
        // 获取随机数
        for (int i = 0; i < COUNT; i++) {
            new Thread(() -> System.out.println(ThreadLocalRandom.current().nextInt(100))).start();
        }
    }

    /**
     * 常量值
     */
    private static final Integer COUNT = 10;
}
