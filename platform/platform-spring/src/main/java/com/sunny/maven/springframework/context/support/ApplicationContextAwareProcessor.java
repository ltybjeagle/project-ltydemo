package com.sunny.maven.springframework.context.support;

import com.sunny.maven.springframework.beans.BeansException;
import com.sunny.maven.springframework.beans.factory.config.BeanPostProcessor;
import com.sunny.maven.springframework.context.ApplicationContext;
import com.sunny.maven.springframework.context.ApplicationContextAware;

/**
 * @author SUNNY
 * @description: 通过 BeanPostProcessor 实现类感知应用上下文对象
 * @create: 2023/5/28 13:02
 */
public class ApplicationContextAwareProcessor implements BeanPostProcessor {

    private final ApplicationContext applicationContext;

    public ApplicationContextAwareProcessor(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof ApplicationContextAware)
            ((ApplicationContextAware) bean).setApplicationContext(applicationContext);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
