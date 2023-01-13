package com.sunny.maven.core.extend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;

/**
 * @author SUNNY
 * @description: ExtBeanFactoryAware 发生在bean的实例化之后，注入属性之前，也就是Setter之前
 *               可以在bean实例化之后，但还未初始化之前，拿到 BeanFactory，在这个时候，可以对每个bean作特殊化的定制。
 *               也或者可以把BeanFactory拿到进行缓存，日后使用
 * @create: 2022-10-18 15:06
 */
@Slf4j
public class ExtBeanFactoryAware implements BeanFactoryAware {
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.debug("13.[BeanFactoryAware] setBeanFactory");
    }
}
