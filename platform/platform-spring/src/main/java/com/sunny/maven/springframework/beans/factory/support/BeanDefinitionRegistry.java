package com.sunny.maven.springframework.beans.factory.support;

import com.sunny.maven.springframework.beans.factory.config.BeanDefinition;

/**
 * @author SUNNY
 * @description: Bean 定义注册接口
 * @create: 2022-12-15 09:46
 */
public interface BeanDefinitionRegistry {

    /**
     * 向注册表中注册 BeanDefinition
     * @param beanName Bean 名称
     * @param beanDefinition Bean 定义
     */
    void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
}
