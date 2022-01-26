package com.sunny.maven.core.configuration;

import com.sunny.maven.core.interceptor.LanguageInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author SUNNY
 * @description: WEB配置类
 * @create: 2021-11-17 14:11
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    /**
     * 注册拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LanguageInterceptor()).addPathPatterns("/**");
    }
}
