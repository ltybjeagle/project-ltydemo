package com.sunny.maven.core.common.spring.scope;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: 新定义的Scope注入到spring容器中
 * @create: 2023-02-15 17:17
 */
@Component
public class ThreadLocalBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory)
            throws BeansException {
        configurableListableBeanFactory.registerScope("threadLocalScope", new ThreadLocalScope());
    }
}
