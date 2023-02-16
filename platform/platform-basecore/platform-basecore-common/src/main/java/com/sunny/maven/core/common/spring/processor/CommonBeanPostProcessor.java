package com.sunny.maven.core.common.spring.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: Spring bean对象初始化
 * @create: 2023-02-15 22:58
 */
@Component
public class CommonBeanPostProcessor implements BeanPostProcessor {

    /**
     * 在初始化方法之前调用
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * 再初始化方法之后调用
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
