package com.sunny.maven.core.demo.concurrency.abstractfactory;

/**
 * @author SUNNY
 * @description:
 * @create: 2023/8/7 15:57
 */
public class BlackCat implements ICat {
    @Override
    public void eat() {
        System.out.println("The black cat is eating!");
    }
}
