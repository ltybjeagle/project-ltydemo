package com.sunny.maven.spring.xmlbean;

/**
 * @author SUNNY
 * @description: 静态工厂
 * @create: 2021-07-28 16:49
 */
public class PersonStaticBeanFactory {

    /**
     * 创建BEAN对象
     * @param name 名称
     * @param age 年龄
     * @return BEAN
     */
    public static PersonService createPersonService(String name, int age) {
        return new PersonService(name, age);
    }
}
