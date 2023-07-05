package com.sunny.maven.core.config;

import com.sunny.maven.core.handlermapping.PathVersionHandlerMapping;
import com.sunny.maven.core.interceptor.LogInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SUNNY
 * @description: config配置
 * @create: 2022-09-24 13:55
 */
@Configuration
@ComponentScan(basePackages = {"com.sunny.maven.core.common.spring", "com.sunny.maven.core.handle"})
public class WebMvcConfig implements WebMvcConfigurer, WebMvcRegistrations {

    /**
     * 不拦截的 url 集合
     */
    List<String> excludes = new ArrayList<>() {
        private static final long serialVersionUID = -6091858404653724029L;

        {
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

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new PathVersionHandlerMapping();
    }
}
