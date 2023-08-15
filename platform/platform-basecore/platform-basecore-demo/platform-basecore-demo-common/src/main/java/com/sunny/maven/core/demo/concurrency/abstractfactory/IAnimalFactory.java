package com.sunny.maven.core.demo.concurrency.abstractfactory;

/**
 * @author SUNNY
 * @description:
 * @create: 2023/8/7 15:55
 */
public interface IAnimalFactory {
    /**
     * 定义创建ICat接口实例的方法
     * @return
     */
    ICat createCat();

    /**
     * 定义创建IDog接口实例的方法
     * @return
     */
    IDog createDog();
}
