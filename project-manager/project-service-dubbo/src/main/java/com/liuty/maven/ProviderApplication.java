package com.liuty.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 类注解说明：
 *      1、注解@SpringBootApplication：标注此应用是SPRING BOOT应用
 *
 * @Description: Spring Boot应用
 *
 */
@SpringBootApplication
public class ProviderApplication {
    public static void main(String[] args) {
        /**
         * Spring应用加载指定的配置文件
         */
//        SpringApplication application = new SpringApplication(
//                new ClassPathXmlApplicationContext("classpath*:/spring/*.xml")
//                , ProviderApplication.class);
//        application.run(args);
        SpringApplication.run(ProviderApplication.class, args);
    }
}
