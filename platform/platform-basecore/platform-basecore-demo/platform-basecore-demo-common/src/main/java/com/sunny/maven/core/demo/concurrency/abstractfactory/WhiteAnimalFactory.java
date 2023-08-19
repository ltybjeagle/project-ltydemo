package com.sunny.maven.core.demo.concurrency.abstractfactory;

/**
 * @author SUNNY
 * @description:
 * @create: 2023/8/7 16:02
 */
public class WhiteAnimalFactory implements IAnimalFactory {
    @Override
    public ICat createCat() {
        return new WhiteCat();
    }

    @Override
    public IDog createDog() {
        return new WhiteDog();
    }
}
