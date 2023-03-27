package com.sunny.maven.springframework.test.common;

import com.sunny.maven.springframework.beans.BeansException;
import com.sunny.maven.springframework.beans.factory.config.BeanPostProcessor;
import com.sunny.maven.springframework.test.bean.UserService;

/**
 * @author SUNNY
 * @description: BeanPostProcessor 在 Bean 对象执行初始化方法前后进行扩展
 * @create: 2023-03-27 10:34
 */
public class MyBeanPostProcessor implements BeanPostProcessor {
    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if ("userService".equals(beanName)) {
            UserService userService = (UserService) bean;
            userService.setLocation("改为：北京");
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }
}
