package com.sunny.maven.core.common.spring.aware;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author SUNNY
 * @description: spring容器对象
 * @create: 2023-02-16 10:44
 */
public class CommonContextAware implements ApplicationContextAware, BeanFactoryAware {

    protected BeanFactory beanFactory;
    protected ApplicationContext context;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
