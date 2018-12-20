package com.liuty.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProviderApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ProviderApplication.class
                , "classpath*:/spring/*.xml");
        application.run(args);
    }
}
