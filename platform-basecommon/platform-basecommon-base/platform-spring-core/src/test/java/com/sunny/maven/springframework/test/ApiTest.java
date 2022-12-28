package com.sunny.maven.springframework.test;

import com.sunny.maven.springframework.beans.PropertyValue;
import com.sunny.maven.springframework.beans.PropertyValues;
import com.sunny.maven.springframework.beans.factory.config.BeanDefinition;
import com.sunny.maven.springframework.beans.factory.config.BeanReference;
import com.sunny.maven.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.sunny.maven.springframework.test.bean.UserDao;
import com.sunny.maven.springframework.test.bean.UserService;
import org.junit.Test;

/**
 * @author SUNNY
 * @description: 测试类
 * @create: 2022-12-14 10:33
 */
public class ApiTest {
    @Test
    public void test_BeanFactory() {
        // 1、初始化BeanFactory接口
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();

        // 2、注册 UserDao
        beanFactory.registerBeanDefinition("userDao", new BeanDefinition(UserDao.class));

        // 3、使用 UserService填充属性（id、userDao）
        PropertyValues propertyValues = new PropertyValues();
        propertyValues.addPropertyValue(new PropertyValue("id", "10001"));
        propertyValues.addPropertyValue(new PropertyValue("userDao", new BeanReference("userDao")));

        // 4、注册Bean对象
        BeanDefinition beanDefinition = new BeanDefinition(UserService.class, propertyValues);
        beanFactory.registerBeanDefinition("userService", beanDefinition);

        // 5、获取Bean对象
        UserService userService = (UserService) beanFactory.getBean("userService", "SUNNY");
        userService.queryUserInfo();
    }
}
