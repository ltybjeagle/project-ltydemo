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
 *      1、服务提供方，将服务注册到注册中心
 *      2、服务消费端，将服务注册到注册中心，将服务列表缓存到本地，根据负载策略调用服务提供方
 *      3、Eureka客户端通过Rest方式调用注册中心服务端同步信息，服务端使用双层MAP保存注册信息
 *          第一层MAP：key:服务名    value:第二层MAP         同一服务多实例集群部署
 *          第二层MAP：key:实例名    value:具体实例信息
 *      4、提供集群高可用支持，存在同步延迟
 *          集群里多个注册中心通过互连（互相配置注册中心服务地址），将信息同步，客户端注册信息到一个节点，
 *          服务节点将注册信息同步到其他节点
 */
@SpringBootApplication
@EnableEurekaServer
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
