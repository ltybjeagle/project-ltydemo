package com.sunny.maven.springframework.test.common;

import com.sunny.maven.springframework.beans.BeansException;
import com.sunny.maven.springframework.beans.PropertyValue;
import com.sunny.maven.springframework.beans.PropertyValues;
import com.sunny.maven.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.sunny.maven.springframework.beans.factory.config.BeanDefinition;
import com.sunny.maven.springframework.beans.factory.config.BeanFactoryPostProcessor;

/**
 * @author SUNNY
 * @description: BeanFactoryPostProcessor 实例化 Bean 对象之前，修改 BeanDefinition 属性
 * @create: 2023-03-27 10:37
 */
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        BeanDefinition beanDefinition = beanFactory.getBeanDefinition("userService");
        PropertyValues propertyValues = beanDefinition.getPropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("company", "改为：字节跳动"));
    }
}
