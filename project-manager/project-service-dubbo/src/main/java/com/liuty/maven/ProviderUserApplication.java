package com.liuty.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProviderUserApplication {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(ProviderUserApplication.class
                , "classpath*:/spring/*.xml");
        application.run(args);
    }
}
