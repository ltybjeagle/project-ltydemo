package com.sunny.maven.rpc.demo.spring.boot.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author SUNNY
 * @description: 服务提供者整合SpringBoot的启动类
 * @create: 2023-02-16 17:59
 */
@SpringBootApplication
@ComponentScan(value = {"com.sunny.maven.rpc"})
public class SpringBootProviderDemoStarter {
    public static void main(String[] args) {
        SpringApplication.run(SpringBootProviderDemoStarter.class, args);
    }
}
