package com.sunny.maven.core.extend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

/**
 * @author SUNNY
 * @description: ExtBeanDefinitionRegistryPostProcessor
 *               读取项目中的beanDefinition之后执行，可以在这里动态注册自己的beanDefinition，可以加载classpath之外的bean
 * @create: 2022-10-17 23:30
 */
@Slf4j
public class ExtBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry)
            throws BeansException {
        log.debug("2.[BeanDefinitionRegistryPostProcessor] --> postProcessBeanDefinitionRegistry");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory)
            throws BeansException {
        log.debug("3.[BeanDefinitionRegistryPostProcessor] --> postProcessBeanFactory");
    }
}
