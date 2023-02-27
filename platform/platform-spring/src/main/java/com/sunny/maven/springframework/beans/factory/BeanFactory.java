package com.sunny.maven.springframework.beans.factory;

import com.sunny.maven.springframework.beans.BeansException;

/**
 * @author SUNNY
 * @description: 定义 Bean 工厂接口
 * @create: 2022-12-14 10:11
 */
public interface BeanFactory {
    /**
     * 返回 Bean 的实例对象
     * @param name 要检索的bean的名称
     * @return 实例化的 Bean 对象
     * @throws BeansException 不能获取 Bean 对象，则抛出异常
     */
    Object getBean(String name) throws BeansException;
    /**
     * 返回含构造函数的 Bean 实例对象
     * @param name 要检索的bean的名称
     * @param args 构造函数入参
     * @return 实例化的 Bean 对象
     * @throws BeansException 不能获取 Bean 对象，则抛出异常
     */
    Object getBean(String name, Object ...args) throws BeansException;
}
