package com.liuty.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 类注解说明：
 *      1、注解@SpringBootApplication：标注此应用是SPRING BOOT应用
 *      2、注解@EnableDiscoveryClient：标注为eureka客户端
 *      3、注解@ServletComponentScan：WEB应用注解，自动扫描加载servlet组件
 *      4、注解@LoadBalanced：基于ribbon组件，提供负载均衡策略，默认采用轮训的策略
 *
 * @Description: Spring Boot应用
 *
 */

@SpringBootApplication
@EnableDiscoveryClient
//@EnableFeignClients
@ServletComponentScan(basePackages = {"com.liuty.maven.filter"})
public class MainApplication {

    @Bean
    @LoadBalanced
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(MainApplication.class, args);
    }
}
