package com.sunny.maven.springframework.beans.factory.support;

import com.sunny.maven.springframework.beans.BeansException;
import com.sunny.maven.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Constructor;

/**
 * @author SUNNY
 * @description: Bean 实例化策略接口
 * @create: 2022-12-15 10:59
 */
public interface InstantiationStrategy {
    /**
     * Bean 实例化接口
     * @param beanDefinition
     * @param beanName
     * @param ctor
     * @param args
     * @return
     * @throws BeansException
     */
    Object instantiate(BeanDefinition beanDefinition, String beanName, Constructor ctor, Object[] args)
            throws BeansException;
}
