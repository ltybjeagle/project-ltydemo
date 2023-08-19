package com.sunny.maven.core.demo.concurrency.abstractfactory;

/**
 * @author SUNNY
 * @description:
 * @create: 2023/8/7 16:00
 */
public class WhiteCat implements ICat {
    @Override
    public void eat() {
        System.out.println("The white cat is eating!");
    }
}
