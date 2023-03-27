package com.sunny.maven.springframework.beans.factory.config;

import com.sunny.maven.springframework.beans.BeansException;

/**
 * @author SUNNY
 * @description: 用于修改新实例化 Bean 对象的扩展点
 * @create: 2023-03-26 16:03
 */
public interface BeanPostProcessor {

    /**
     * 在 Bean 对象执行初始化方法之前，执行此方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException;

    /**
     * 在 Bean 对象执行初始化方法之后，执行此方法
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException;
}
