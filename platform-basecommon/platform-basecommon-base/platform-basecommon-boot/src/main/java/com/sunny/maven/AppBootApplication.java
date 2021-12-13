package com.sunny.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * @author SUNNY
 * @description: Boot启动类
 * @create: 2021-11-10 10:09
 */
@SpringBootApplication
@EnableAsync
public class AppBootApplication {
    public static void main(String[] args) {
        SpringApplication.run(AppBootApplication.class, args);
    }
}
