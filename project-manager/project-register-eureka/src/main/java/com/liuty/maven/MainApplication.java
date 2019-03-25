package com.liuty.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * 类注解说明：
 *      1、注解@SpringBootApplication：标注此应用是SPRING BOOT应用
 *      2、注解@EnableEurekaServer：设置此应用为注册中心服务端进程
 *
 * @Description: 服务注册、发现应用
 *      1、服务提供方，将服务应用注册到服务端
 *      2、服务消费端，查看服务提供列表，缓存到本地，根据负载策略调用服务提供端
 *      3、提供集群高可用支持，存在同步延迟
 */
@SpringBootApplication
@EnableEurekaServer
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
