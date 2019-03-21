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
//@EnableEurekaClient
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProviderApplication.class, args);
    }
}
