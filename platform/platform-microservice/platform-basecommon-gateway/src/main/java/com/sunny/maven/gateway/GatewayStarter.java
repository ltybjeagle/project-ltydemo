package com.sunny.maven.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author SUNNY
 * @description: 服务网关启动类
 * @create: 2022-07-01 11:22
 */
@SpringBootApplication
@EnableFeignClients
public class GatewayStarter {
    public static void main(String[] args) {
        SpringApplication.run(GatewayStarter.class, args);
    }
}
