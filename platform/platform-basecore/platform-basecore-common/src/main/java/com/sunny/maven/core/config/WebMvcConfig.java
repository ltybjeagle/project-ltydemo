package com.sunny.maven.core.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.sunny.maven.core.handlermapping.PathVersionHandlerMapping;
import com.sunny.maven.core.interceptor.TraceIdInterceptor;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author SUNNY
 * @description: config配置
 * @create: 2022-09-24 13:55
 */
@Configuration
@ComponentScan(
        basePackages = {"com.sunny.maven.core.common.spring",
                "com.sunny.maven.core.handle",
                "com.sunny.maven.core.runner"})
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
        registry.addInterceptor(new TraceIdInterceptor()).addPathPatterns("/**").excludePathPatterns(excludes);
    }

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return new PathVersionHandlerMapping();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
                addResourceHandler("/templates/**", "/**").
                addResourceLocations("classpath:/templates/", "classpath:/static/");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig config = new FastJsonConfig();
        config.setDateFormat("yyyy-MM-dd");
        config.setCharset(StandardCharsets.UTF_8);
        config.setSerializerFeatures(
                SerializerFeature.WriteClassName,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.PrettyFormat,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty);
        converter.setFastJsonConfig(config);
        converters.add(converter);
    }
}
