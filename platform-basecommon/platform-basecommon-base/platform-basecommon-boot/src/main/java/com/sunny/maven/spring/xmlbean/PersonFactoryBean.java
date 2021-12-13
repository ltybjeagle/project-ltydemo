package com.sunny.maven.spring.xmlbean;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author SUNNY
 * @description: 实例工厂BEAN
 * @create: 2021-07-28 17:32
 */
public class PersonFactoryBean implements FactoryBean<PersonService> {
    /**
     * 获取BEAN对象
     * @return BEAN对象
     * @throws Exception 异常
     */
    @Override
    public PersonService getObject() throws Exception {
        return new PersonService();
    }

    /**
     * BEAN对象类型
     * @return Class
     */
    @Override
    public Class<?> getObjectType() {
        return PersonService.class;
    }
}
