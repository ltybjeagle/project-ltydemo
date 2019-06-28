package com.liuty.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Description: 微服务应用，spring cloud服务消费方
 * spring security要素：
 * 1、AuthenticationManager：用户认证
 * 2、AccessDecisionManager：资源访问管理
 * 3、AbstractSecurityInterceptor：执行控制
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.liuty.maven.service"})
@ServletComponentScan(basePackages = {"com.liuty.maven.filter"})
public class MainApplication {

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
