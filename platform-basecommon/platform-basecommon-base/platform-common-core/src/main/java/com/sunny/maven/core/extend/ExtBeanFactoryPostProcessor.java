package com.sunny.maven.core.extend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author SUNNY
 * @description: ExtBeanFactoryPostProcessor
 *               spring读取beanDefinition信息之后，实例化bean之前，通过实现这个扩展接口来自行处理一些东西，
 *               比如修改已经注册的beanDefinition的元信息
 * @create: 2022-10-17 23:43
 */
@Slf4j
public class ExtBeanFactoryPostProcessor implements BeanFactoryPostProcessor {
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory)
            throws BeansException {
        log.debug("4.[BeanFactoryPostProcessor] --> postProcessBeanFactory");
    }
}
