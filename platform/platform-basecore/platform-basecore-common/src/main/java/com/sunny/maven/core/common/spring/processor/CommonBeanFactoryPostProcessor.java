package com.sunny.maven.core.common.spring.processor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: spring bean对象实例化扩展
 * @create: 2023-02-16 10:57
 */
@Component
public class CommonBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory)
            throws BeansException {

    }
}
