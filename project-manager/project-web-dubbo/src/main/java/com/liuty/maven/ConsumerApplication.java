package com.liuty.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ConsumerApplication {

    public static void main(String ...args) {
        SpringApplication application = new SpringApplication(ConsumerApplication.class);
        application.run(args);
    }
}
