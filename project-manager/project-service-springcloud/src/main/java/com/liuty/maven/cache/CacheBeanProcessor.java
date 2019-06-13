package com.liuty.maven.cache;

import com.liuty.maven.annotation.CacheDevPf;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @描述:
 * @创建人: Sunny
 * @创建时间: 2019/5/30
 */
@Slf4j
@Component
public class CacheBeanProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        CacheDevPf cdp = bean.getClass().getAnnotation(CacheDevPf.class);
        if (cdp != null) {
            log.info("获取缓存对象");
            CacheProxyObject proxyObject = new CacheProxyObject(bean);
            return proxyObject.getProxyInstance();
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
