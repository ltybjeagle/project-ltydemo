package com.sunny.maven.rpc.demo.docker.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author SUNNY
 * @description: Rpc框架接入Docker的服务提供者启动类
 * @create: 2023-02-20 14:58
 */
@SpringBootApplication
@ComponentScan(value = {"com.sunny.maven.rpc"})
public class DockerProviderDemoStarter {
    public static void main(String[] args) {
        SpringApplication.run(DockerProviderDemoStarter.class, args);
    }
}
