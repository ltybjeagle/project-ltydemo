package com.sunny.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/23 15:26
 * @description：GateWay网关
 * @modified By：
 * @version: 1.0.0
 */
@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {
    public static void main(String ...args) throws Exception {
        SpringApplication.run(GatewayApplication.class, args);
    }
}
