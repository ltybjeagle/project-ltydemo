package com.sunny.maven.core.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author SUNNY
 * @description: 认证配置类
 * @create: 2022-01-16 18:55
 */
@Configuration
@ComponentScan(basePackages = {"com.sunny.maven.core.security"})
public class AuthConfiguration {
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
