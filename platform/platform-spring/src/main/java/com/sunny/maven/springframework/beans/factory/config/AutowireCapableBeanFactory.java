package com.sunny.maven.springframework.beans.factory.config;

import com.sunny.maven.springframework.beans.factory.BeanFactory;

/**
 * @author SUNNY
 * @description: Extension of the {@link com.sunny.maven.springframework.beans.factory.BeanFactory}
 * interface to be implemented by bean factories that are capable of
 * autowiring, provided that they want to expose this functionality for
 * existing bean instances.
 * @create: 2023-03-25 13:53
 */
public interface AutowireCapableBeanFactory extends BeanFactory {
}
