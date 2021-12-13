package com.sunny.maven.configurer;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author SUNNY
 * @description: WebConfig
 * @create: 2021-11-24 12:19
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * 解决  No mapping for GET /favicon.ico 访问静态资源图标
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/")
                .addResourceLocations("classpath:/META-INF/resources/");
    }
}
