package com.sunny.maven.core.configuration;

import com.sunny.maven.core.filter.GlobalRequestContextFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author SUNNY
 * @description: Webflux配置类
 * @create: 2022-10-09 14:50
 */
@Configuration
@ComponentScan(basePackages = {"com.sunny.maven.core.advice", "com.sunny.maven.core.controller",
        "com.sunny.maven.core.handlers"})
public class WebfluxConfig {
    @Bean
    public GlobalRequestContextFilter globalRequestContextFilter() {
        return new GlobalRequestContextFilter();
    }
}
