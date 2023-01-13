package com.sunny.maven.core.configuration;

import com.sunny.maven.core.filter.GlobalRequestContextServletFilter;
import com.sunny.maven.core.interceptor.LogInterceptor;
import com.sunny.maven.core.interceptor.PlatFormAsyncHandlerInterceptor;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author SUNNY
 * @description: config配置
 * @create: 2022-09-24 13:55
 */
@Configuration
@ComponentScan(basePackages = {"com.sunny.maven.core.exception", "com.sunny.maven.core.handle",
        "com.sunny.maven.core.feign"})
@ServletComponentScan(basePackages = {"com.sunny.maven.core.servlet"})
public class WebMvcConfig implements WebMvcConfigurer {

    @Bean
    public GlobalRequestContextServletFilter globalRequestContextServletFilter() {
        return new GlobalRequestContextServletFilter();
    }

    /**
     * 异步调用拦截器
     * @return PlatFormAsyncHandlerInterceptor
     */
    @Bean
    public PlatFormAsyncHandlerInterceptor platFormAsyncHandlerInterceptor() {
        return new PlatFormAsyncHandlerInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**");
    }
}
