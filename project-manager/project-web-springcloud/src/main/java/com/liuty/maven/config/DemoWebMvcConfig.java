package com.liuty.maven.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @描述: 定制Spring MVC配置
 *
 * @创建人: Sunny
 * @创建时间: 2019/6/25
 */
@Configuration
@EnableWebMvc
public class DemoWebMvcConfig implements WebMvcConfigurer {
    /**
     * spring-boot-starter-web默认加载配置：
     * 1、加载ViewResolver
     * 2、加载Converter
     * 3、加载HttpMessageConverter
     * 4、加载MessageCodesResolver
     */
}
