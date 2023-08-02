package com.sunny.maven.core.demo.concurrency.singleton;

/**
 * @author SUNNY
 * @description: 懒汉模式，单例实例在第一次使用的时候进行创建，这个类是线程不安全的
 * @create: 2023/8/2 17:57
 */
public class SingletonExample1 {
    private SingletonExample1() {}

    private static SingletonExample1 instance = null;

    public static SingletonExample1 getInstance() {
        // 多个线程同时调用，可能会创建多个对象
        if (instance == null) {
            instance = new SingletonExample1();
        }
        return instance;
    }
}
