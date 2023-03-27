package com.sunny.maven.springframework.beans.factory.config;

import com.sunny.maven.springframework.beans.BeansException;
import com.sunny.maven.springframework.beans.factory.ConfigurableListableBeanFactory;

/**
 * @author SUNNY
 * @description: 允许自定义修改 BeanDefinition 属性信息
 * @create: 2023-03-26 15:59
 */
public interface BeanFactoryPostProcessor {

    /**
     * 在所有的 BeanDefinition 加载完成后，实例化 Bean 对象之前，提供修改 BeanDefinition 属性的机制
     * @param beanFactory
     * @throws BeansException
     */
    void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException;
}
