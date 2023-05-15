package com.sunny.maven.springframework.beans.factory.support;

import cn.hutool.core.util.StrUtil;
import com.sunny.maven.springframework.beans.BeansException;
import com.sunny.maven.springframework.beans.factory.DisposableBean;
import com.sunny.maven.springframework.beans.factory.config.BeanDefinition;

import java.lang.reflect.Method;

/**
 * @author SUNNY
 * @description: Adapter that implements the {@link DisposableBean} and {@link Runnable} interfaces
 * performing various destruction steps on a given bean instance:
 * @create: 2023-05-15 05:53
 */
public class DisposableBeanAdapter implements DisposableBean {

    private final Object bean;
    private final String beanName;
    private String destroyMethodName;

    public DisposableBeanAdapter(Object bean, String beanName, BeanDefinition beanDefinition) {
        this.bean = bean;
        this.beanName = beanName;
        this.destroyMethodName = beanDefinition.getDestroyMethodName();
    }

    @Override
    public void destroy() throws Exception {
        // 1. 实现接口 DisposableBean
        if (bean instanceof DisposableBean) {
            ((DisposableBean) bean).destroy();
        }

        // 2. 注解配置 destroy-method {判断是为了避免二次执行销毁}
        if (StrUtil.isNotEmpty(destroyMethodName) &&
                !(bean instanceof DisposableBean && "destroy".equals(destroyMethodName))) {
            Method destroyMethod = bean.getClass().getMethod(destroyMethodName);
            if (null == destroyMethod) {
                throw new BeansException("Couldn't find a destroy method named '" + destroyMethodName +
                        "' on bean with name '" + beanName + "'");
            }
            destroyMethod.invoke(bean);
        }
    }
}
