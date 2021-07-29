package com.sunny.maven.spring;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
@ComponentScan(basePackages = "com.sunny.maven.spring.annotation")
@SpringBootApplication
public class ApplicationOneDemo {
    /**
     * new SpringApplicationBuilder(Application.class).web(WebApplicationType.SERVLET).run(args);
     * @param args 参数
     */
    public static void main(String ...args) {
        ApplicationContext ctx = SpringApplication.run(ApplicationOneDemo.class);
        // 获取定义BEAN
        String[] beanNames = ctx.getBeanDefinitionNames();
        log.info("bean总数:{}", ctx.getBeanDefinitionCount());
        Arrays.stream(beanNames).forEach(System.out::println);
    }


}
