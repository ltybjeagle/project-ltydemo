package com.sunny.maven.middle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author SUNNY
 * @description: 中台服务启动类
 * @create: 2023/6/21 17:32
 */
@SpringBootApplication
@ComponentScan(
        value = {
                "com.sunny.maven.user",
                "com.sunny.maven.middle.thymeleaf",
                "com.sunny.maven.middle.aspect"})
@ServletComponentScan
@EnableAspectJAutoProxy
public class MiddleStarter {
    public static void main(String[] args) {
        SpringApplication.run(MiddleStarter.class, args);
    }
}
