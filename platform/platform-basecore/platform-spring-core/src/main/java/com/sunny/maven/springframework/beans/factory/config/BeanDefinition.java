package com.sunny.maven.springframework.beans.factory.config;

import com.sunny.maven.springframework.beans.PropertyValues;

/**
 * @author SUNNY
 * @description: 定义 Bean 实例信息
 * @create: 2022-12-14 10:07
 */
public class BeanDefinition {
    private Class beanClass;
    private PropertyValues propertyValues;

    /**
     * 构造函数
     * @param beanClass
     */
    public BeanDefinition(Class beanClass) {
        this.beanClass = beanClass;
        this.propertyValues = new PropertyValues();
    }

    /**
     * 构造函数
     * @param beanClass
     * @param propertyValues
     */
    public BeanDefinition(Class beanClass, PropertyValues propertyValues) {
        this.beanClass = beanClass;
        this.propertyValues = propertyValues != null ? propertyValues : new PropertyValues();
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

    public PropertyValues getPropertyValues() {
        return propertyValues;
    }

    public void setPropertyValues(PropertyValues propertyValues) {
        this.propertyValues = propertyValues;
    }
}
