package com.sunny.maven.core.demo.concurrency.singleton;

/**
 * @author SUNNY
 * @description: 饿汉模式，单例实例在类装载的时候进行创建，是线程安全的
 * @create: 2023/8/2 18:48
 */
public class SingletonExample6 {
    private SingletonExample6() {}

    private static SingletonExample6 instance = null;

    static {
        instance = new SingletonExample6();
    }

    public static SingletonExample6 getInstance() {
        return instance;
    }
}
