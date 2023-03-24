package com.sunny.maven.microservice.order.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author SUNNY
 * @description: 配置类
 * @create: 2023-03-23 14:09
 */
@Configuration
public class LoadBalanceConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
