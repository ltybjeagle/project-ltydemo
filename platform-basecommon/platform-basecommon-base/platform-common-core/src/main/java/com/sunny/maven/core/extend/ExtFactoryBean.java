package com.sunny.maven.core.extend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author SUNNY
 * @description: FactoryBean 通过实现该接口定制实例化Bean的逻辑
 *               用户可以扩展这个类，来为要实例化的bean作一个代理，比如为该对象的所有的方法作一个拦截，
 *               在调用前后输出一行log，模仿ProxyFactoryBean的功能
 * @create: 2022-10-18 16:25
 */
@Slf4j
public class ExtFactoryBean implements FactoryBean<ExtFactoryBean.ExtFactoryBeanInner> {


    @Override
    public ExtFactoryBeanInner getObject() throws Exception {
        log.info("18.[FactoryBean] getObject");
        return new ExtFactoryBean.ExtFactoryBeanInner();
    }

    @Override
    public Class<?> getObjectType() {
        return ExtFactoryBean.ExtFactoryBeanInner.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public static class ExtFactoryBeanInner {}
}
