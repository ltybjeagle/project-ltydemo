package com.liuty.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.support.ClassPathXmlApplicationContext;

@SpringBootApplication
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(
                new ClassPathXmlApplicationContext("classpath*:/spring/*.xml")
                , ProviderApplication.class);
        application.run(args);
    }
}
