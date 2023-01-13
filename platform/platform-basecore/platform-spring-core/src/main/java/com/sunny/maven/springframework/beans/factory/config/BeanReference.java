package com.sunny.maven.springframework.beans.factory.config;

/**
 * @author SUNNY
 * @description: Bean 引用
 * @create: 2022-12-15 17:08
 */
public class BeanReference {
    private final String beanName;

    public BeanReference(String beanName) {
        this.beanName = beanName;
    }

    public String getBeanName() {
        return beanName;
    }
}
