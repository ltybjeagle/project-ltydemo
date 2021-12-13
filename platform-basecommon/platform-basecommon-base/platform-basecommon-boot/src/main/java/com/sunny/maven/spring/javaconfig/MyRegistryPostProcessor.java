package com.sunny.maven.spring.javaconfig;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: 自定义Bean注册——02
 * BeanDefinitionRegistryPostProcessor 侧重于 bean 的注册
 * @create: 2021-07-29 19:08
 */
@Component
public class MyRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry)
            throws BeansException {
        // 定义BEAN信息
        RootBeanDefinition roleBeanDefinition = new RootBeanDefinition(Role.class);
        roleBeanDefinition.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE);
        beanDefinitionRegistry.registerBeanDefinition("role", roleBeanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory)
            throws BeansException {

    }
}
