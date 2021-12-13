package com.sunny.maven.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

/**
 * @author SUNNY
 * @description: SpringBoot启动类
 * @create: 2021-07-28 18:54
 */
@ComponentScan(basePackages = "com.sunny.maven.spring.annotation")
@SpringBootApplication
public class ApplicationOneDemo {
    private static final Logger logger = LoggerFactory.getLogger(ApplicationOneDemo.class);
    /**
     * new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
     * @param args 参数
     */
    public static void main(String ...args) {
        ApplicationContext ctx = SpringApplication.run(ApplicationOneDemo.class);
        // 获取定义BEAN
        String[] beanNames = ctx.getBeanDefinitionNames();
        logger.info("bean总数:{}", ctx.getBeanDefinitionCount());
        Arrays.stream(beanNames).forEach(System.out::println);
    }


}
