package com.sunny.maven.core.demo.concurrency.singleton;

/**
 * @author SUNNY
 * @description: 饿汉模式，单例实例在类装载的时候进行创建，是线程安全的
 * @create: 2023/8/2 18:03
 */
public class SingletonExample2 {
    private SingletonExample2() {}

    private static SingletonExample2 instance = new SingletonExample2();

    public static SingletonExample2 getInstance() {
        return instance;
    }
}
