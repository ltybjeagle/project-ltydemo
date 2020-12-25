package com.sunny.maven.rmrpc.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/4/12 20:34
 * @description：服务提供方实例Bean
 * @modified By：
 * @version: 1.0.0
 */
public class ServerBean<T> implements ApplicationContextAware, FactoryBean {
    private Class<?> interfaceClass;
    private String initBeanId;
    private ApplicationContext applicationContext;

    public Class<?> getInterfaceClass() {
        return interfaceClass;
    }

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public String getInitBeanId() {
        return initBeanId;
    }

    public void setInitBeanId(String initBeanId) {
        this.initBeanId = initBeanId;
    }

    @Override
    public Object getObject() throws Exception {
        return applicationContext.getBean(initBeanId);
    }

    @Override
    public Class<?> getObjectType() {
        return getInterfaceClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
