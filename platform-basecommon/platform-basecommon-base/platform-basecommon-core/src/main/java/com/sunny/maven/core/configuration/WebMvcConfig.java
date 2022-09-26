package com.sunny.maven.core.configuration;

import com.sunny.maven.core.filter.GlobalRequestContextServletFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author SUNNY
 * @description: config配置
 * @create: 2022-09-24 13:55
 */
@Configuration
@ComponentScan(basePackages = {"com.sunny.maven.core.exception", "com.sunny.maven.core.handle"})
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public GlobalRequestContextServletFilter globalRequestContextServletFilter() {
        return new GlobalRequestContextServletFilter();
    }
}
