package com.sunny.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/23 11:39
 * @description：注册中心
 * @modified By：
 * @version: 1.0.0
 */
@SpringBootApplication
@EnableEurekaServer
public class EurekaServerApplication {
    public static void main(String ...args) throws Exception {
        SpringApplication.run(EurekaServerApplication.class, args);
    }
}
