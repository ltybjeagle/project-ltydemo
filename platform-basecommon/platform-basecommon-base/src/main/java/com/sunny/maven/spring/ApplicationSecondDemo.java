package com.sunny.maven.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

/**
 * @author SUNNY
 * @description: SpringBoot启动类
 * @create: 2021-07-28 22:05
 */
@SpringBootApplication(scanBasePackages = "com.sunny.maven.spring.annotation")
public class ApplicationSecondDemo {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationSecondDemo.class);
    public static void main(String ...args) {
        ApplicationContext ctx = SpringApplication.run(ApplicationSecondDemo.class);
        // 获取定义BEAN
        String[] beanNames = ctx.getBeanDefinitionNames();
        logger.info("bean总数:{}", ctx.getBeanDefinitionCount());
        Arrays.stream(beanNames).forEach(System.out::println);
    }
}
