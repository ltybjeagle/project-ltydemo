package com.sunny.maven.core.config;

import com.sunny.maven.core.interceptor.LogInterceptor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SUNNY
 * @description: config配置
 * @create: 2022-09-24 13:55
 */
@Configuration
@ComponentScan(basePackages = {"com.sunny.maven.core.common.spring", "com.sunny.maven.core.handle"})
public class WebMvcConfig implements WebMvcConfigurer {

    /**
     * 不拦截的 url 集合
     */
    List<String> excludes = new ArrayList<String>() {{
        add("/**/*.html");
        add("/js/**");
        add("/editor.md/**");
        add("/css/**");
        add("/img/**"); // 放行 static/img 下的所有文件
    }};

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogInterceptor()).addPathPatterns("/**").excludePathPatterns(excludes);
    }
}
