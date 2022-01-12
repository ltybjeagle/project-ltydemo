package com.sunny.maven.eureka.config;

import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author SUNNY
 * @description: Eureka配置类
 * @create: 2021-11-30 14:18
 */
@Configuration
@EnableDiscoveryClient
@ComponentScan(basePackages = {"com.sunny.maven.eureka"})
public class EurekaConfiguration {
}
