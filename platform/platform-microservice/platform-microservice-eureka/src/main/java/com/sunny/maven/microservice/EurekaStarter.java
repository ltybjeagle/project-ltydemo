package com.sunny.maven.microservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author SUNNY
 * @description: Eureka注册中心
 * @create: 2023-02-14 10:24
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaStarter {
    public static void main(String[] args) {
        SpringApplication.run(EurekaStarter.class, args);
    }
}
