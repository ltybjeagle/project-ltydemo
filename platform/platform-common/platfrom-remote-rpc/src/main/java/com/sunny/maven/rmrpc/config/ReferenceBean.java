package com.sunny.maven.rmrpc.config;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/3/21 21:32
 * @description：引用BEAN定义
 * @modified By：
 * @version: 1.0.0
 */
public class ReferenceBean<T> extends ReferenceConfig<T> implements FactoryBean {
    @Override
    public Object getObject() throws Exception {
        return get();
    }

    @Override
    public Class<?> getObjectType() {
        return getInterfaceClass();
    }

    @Override
    public boolean isSingleton() {
        return true;
    }
}
