package com.sunny.maven.pagecore.annotation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/5/10 19:41
 * @description：WebMVC配置类
 * @modified By：
 * @version: 1.0.0
 */
@Configuration
public class WebMvcConfigurer implements org.springframework.web.servlet.config.annotation.WebMvcConfigurer {

    @Override
    public void configureViewResolvers(ViewResolverRegistry registry) {
        registry.jsp("/", ".jsp");
        registry.enableContentNegotiation(new MappingJackson2JsonView());
    }
}
