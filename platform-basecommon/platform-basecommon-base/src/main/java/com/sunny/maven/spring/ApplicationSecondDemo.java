package com.sunny.maven.spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Arrays;

/**
 * @author SUNNY
 * @description: SpringBoot启动类
 * @create: 2021-07-28 22:05
 */
@Slf4j
@SpringBootApplication(scanBasePackages = "com.sunny.maven.spring.annotation")
public class ApplicationSecondDemo {

    public static void main(String ...args) {
        ApplicationContext ctx = SpringApplication.run(ApplicationSecondDemo.class);
        // 获取定义BEAN
        String[] beanNames = ctx.getBeanDefinitionNames();
        log.info("bean总数:{}", ctx.getBeanDefinitionCount());
        Arrays.stream(beanNames).forEach(System.out::println);
    }
}
