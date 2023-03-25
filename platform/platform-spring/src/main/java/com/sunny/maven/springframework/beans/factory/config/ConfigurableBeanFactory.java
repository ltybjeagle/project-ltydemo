package com.sunny.maven.springframework.beans.factory.config;

import com.sunny.maven.springframework.beans.factory.HierarchicalBeanFactory;

/**
 * @author SUNNY
 * @description: Configuration interface to be implemented by most bean factories. Provides
 * facilities to configure a bean factory, in addition to the bean factory
 * client methods in the {@link com.sunny.maven.springframework.beans.factory.BeanFactory}
 * interface.
 * @create: 2023-03-25 14:07
 */
public interface ConfigurableBeanFactory extends HierarchicalBeanFactory, SingletonBeanRegistry {
    String SCOPE_SINGLETON = "singleton";

    String SCOPE_PROTOTYPE = "prototype";
}
