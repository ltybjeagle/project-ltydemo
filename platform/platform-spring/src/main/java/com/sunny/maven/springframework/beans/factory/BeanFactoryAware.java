package com.sunny.maven.springframework.beans.factory;

import com.sunny.maven.springframework.beans.BeansException;

/**
 * @author SUNNY
 * @description: 实现此接口，既能感知到所属的 BeanFactory
 * @create: 2023/5/28 11:58
 */
public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;
}
