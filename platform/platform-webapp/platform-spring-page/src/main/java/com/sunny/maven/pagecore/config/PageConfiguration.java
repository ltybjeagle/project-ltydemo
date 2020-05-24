package com.sunny.maven.pagecore.annotation.config;

import com.sunny.maven.servlet.PlatFormPageServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/5/3 23:34
 * @description：前端配置类
 * @modified By：
 * @version: 1.0.0
 */
@Configuration
public class PageConfiguration {

    @Bean
    public ServletRegistrationBean regPageDispatcherServletBean() {
        ServletRegistrationBean pageServlet =  new ServletRegistrationBean(new PlatFormPageServlet(), "/");
        pageServlet.setLoadOnStartup(0);
        return pageServlet;
    }
}
