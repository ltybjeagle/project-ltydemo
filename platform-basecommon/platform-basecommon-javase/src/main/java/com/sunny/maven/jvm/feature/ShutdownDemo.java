package com.sunny.maven.jvm.feature;

import java.util.concurrent.TimeUnit;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/30 15:04
 * @description：Java虚拟机关闭钩子
 * @modified By：
 * @version: 1.0.0
 */
public class ShutdownDemo {
    public ShutdownDemo() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Execute Hook.....");
        }));
    }

    public static void main(String ...args) throws Exception {
        new ShutdownDemo();
        try {
            TimeUnit.MILLISECONDS.sleep(5000);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
