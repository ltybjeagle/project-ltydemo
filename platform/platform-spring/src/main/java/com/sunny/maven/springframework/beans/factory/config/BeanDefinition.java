package com.sunny.maven.springframework.beans.factory.config;

/**
 * @author SUNNY
 * @description: Bean 对象信息定义
 * @create: 2023-02-25 22:20
 */
public class BeanDefinition {
    private Class beanClass;

    /**
     * 构造函数
     * @param beanClass
     */
    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
    }

    /**
     * 获取beanClass
     * @return
     */
    public Class getBeanClass() {
        return beanClass;
    }

    /**
     * 设置beanClass
     * @param beanClass
     */
    public void setBeanClass(Class beanClass) {
        this.beanClass = beanClass;
    }
}
