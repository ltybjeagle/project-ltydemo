package com.sunny.maven.core.extend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessor;

/**
 * @author SUNNY
 * @description: ExtInstantiationAwareBeanPostProcessor bean的实例化阶段、属性注入阶段和初始化阶段进行扩展
 *               对实现了某一类接口的bean在各个生命期间进行收集，或者对某个类型的bean进行统一的设值
 * @create: 2022-10-18 14:39
 */
@Slf4j
public class ExtInstantiationAwareBeanPostProcessor implements InstantiationAwareBeanPostProcessor {
    /**
     * 实例化bean之前，相当于new这个bean之前
     * @param beanClass
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInstantiation(Class<?> beanClass, String beanName) throws BeansException {
        log.debug("5.[InstantiationAwareBeanPostProcessor] postProcessBeforeInstantiation before instantiation {}",
                beanName);
        return null;
    }

    /**
     * 实例化bean之后，相当于new这个bean之后
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public boolean postProcessAfterInstantiation(Object bean, String beanName) throws BeansException {
        log.debug("6.[InstantiationAwareBeanPostProcessor] postProcessAfterInstantiation after instantiation {}",
                beanName);
        return true;
    }

    /**
     * bean已经实例化完成，在属性注入时阶段触发，@Autowired,@Resource等注解原理基于此方法实现
     * @param pvs
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName)
            throws BeansException {
        log.debug("7.[InstantiationAwareBeanPostProcessor] postProcessProperties " + beanName);
        return pvs;
    }

    /**
     * 初始化bean之前，相当于把bean注入spring上下文之前
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        log.debug("8.[InstantiationAwareBeanPostProcessor] postProcessBeforeInitialization before initialization {}",
                beanName);
        return bean;
    }

    /**
     * 初始化bean之后，相当于把bean注入spring上下文之后
     * @param bean
     * @param beanName
     * @return
     * @throws BeansException
     */
    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        log.debug("9.[InstantiationAwareBeanPostProcessor] postProcessAfterInitialization after initialization {}",
                beanName);
        return bean;
    }
}
