package com.liuty.maven.command;

import com.liuty.maven.annotation.CacheDevPf;
import org.springframework.beans.BeansException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @描述: 上下文回调入口
 * 多个CommandLineRunner实现类，使用@Order注解标注执行顺序
 *
 * @创建人: Sunny
 * @创建时间: 2019/5/30
 */
@Component
@Order(3)
public class CacheCommandLineRunner implements ApplicationContextAware, CommandLineRunner {

    private ApplicationContext applicationContext;

    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 10; i++) {
            Object obj = applicationContext.getBean("cacheOpObject");
            System.out.println("对象：" + obj);
            System.out.println("对象注解：" + obj.getClass().getAnnotation(CacheDevPf.class));
            Map<String, Object> map =  applicationContext.getBeansWithAnnotation(CacheDevPf.class);
            System.out.println("map对象：" + map);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
