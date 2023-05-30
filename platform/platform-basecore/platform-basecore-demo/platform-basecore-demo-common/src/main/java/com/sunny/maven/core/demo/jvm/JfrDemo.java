package com.sunny.maven.core.demo.jvm;

/**
 * @author SUNNY
 * @description: JFR 是一种轻量级的、低开销的事件记录器，它可以用来记录各种事件，包括线程的生命周期、垃圾回收、类加载、锁竞争等等
 * JFR启动: JVM添加启动参数 -XX:+UnlockCommercialFeatures -XX:+FlightRecorder
 * @create: 2023/5/30 21:58
 */
public class JfrDemo {
    public static void main(String[] args) throws InterruptedException {
        while (true) {
            Thread.sleep(1000);
        }
    }
}
