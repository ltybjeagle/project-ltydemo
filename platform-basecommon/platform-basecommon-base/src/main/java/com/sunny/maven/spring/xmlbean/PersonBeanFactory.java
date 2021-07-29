package com.sunny.maven.spring.xmlbean;

/**
 * @author SUNNY
 * @description: 实例工厂
 * @create: 2021-07-28 17:14
 */
public class PersonBeanFactory {

    /**
     * 创建BEAN对象
     * @param name 名称
     * @param age 年龄
     * @return BEAN
     */
    public PersonService createPersonService(String name, int age) {
        return new PersonService(name, age);
    }
}
