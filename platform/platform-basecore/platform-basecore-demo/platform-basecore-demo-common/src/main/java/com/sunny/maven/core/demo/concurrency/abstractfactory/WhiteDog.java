package com.sunny.maven.core.demo.concurrency.abstractfactory;

/**
 * @author SUNNY
 * @description:
 * @create: 2023/8/7 16:00
 */
public class WhiteDog implements IDog {
    @Override
    public void eat() {
        System.out.println("The white dog is eating!");
    }
}
