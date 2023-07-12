package com.sunny.maven.microservice.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author SUNNY
 * @description: 服务网关启动类
 * @create: 2023-04-01 15:11
 */
@SpringBootApplication
//@EnableDiscoveryClient
public class GateWayStarter {
    public static void main(String[] args) {
        System.setProperty("csp.sentinel.app.type", "1");
        SpringApplication.run(GateWayStarter.class, args);
    }
}
