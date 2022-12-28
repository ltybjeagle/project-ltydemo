package com.sunny.maven.core.extend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanNameAware;

import javax.annotation.PostConstruct;

/**
 * @author SUNNY
 * @description: ExtBeanNameAware 触发点在bean的初始化之前，也就是postProcessBeforeInitialization之前
 *               在初始化bean之前拿到spring容器中注册的的beanName，来自行修改这个beanName的值
 * @create: 2022-10-18 15:52
 */
@Slf4j
public class ExtBeanNameAware implements BeanNameAware {
    @Override
    public void setBeanName(String s) {
        log.debug("15.[BeanNameAware] {}", s);
    }

    public ExtBeanNameAware() {
        log.debug("14.NormalBean constructor");
    }

    @PostConstruct
    public void init() {
        log.debug("16.PostConstruct init");
    }
}
