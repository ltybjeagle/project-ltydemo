package com.sunny.maven.springframework.beans.factory;

/**
 * @author SUNNY
 * @description: Interface to be implemented by beans that want to be aware of their
 * bean name in a bean factory. Note that it is not usually recommended
 * that an object depend on its bean name, as this represents a potentially
 * brittle dependence on external configuration, as well as a possibly
 * unnecessary dependence on a Spring API.
 * @create: 2023/5/28 12:56
 */
public interface BeanNameAware extends Aware {
    void setBeanName(String name);
}
