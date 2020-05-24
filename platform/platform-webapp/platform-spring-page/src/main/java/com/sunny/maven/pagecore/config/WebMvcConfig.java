package com.sunny.maven.pagecore.annotation.config;

import com.sunny.maven.pagecore.exception.ErrorPageInterceptor;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/5/10 17:44
 * @description：WEB配置类
 * @modified By：
 * @version: 1.0.0
 */
@Configuration
@EnableWebMvc
public class WebMvcConfig extends WebMvcConfigurationSupport implements ServletContextInitializer {
    public WebMvcConfig(){
        System.out.println("WebMvcConfig......");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ErrorPageInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**")
                .addResourceLocations("classpath:/static/");
        super.addResourceHandlers(registry);
    }

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        this.setServletContext(servletContext);
    }
}
