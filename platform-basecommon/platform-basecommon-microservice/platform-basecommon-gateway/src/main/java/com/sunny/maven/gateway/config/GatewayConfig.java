package com.sunny.maven.gateway.config;

import com.sunny.maven.gateway.filter.GlobalGatewayAuthFilter;
import com.sunny.maven.gateway.filter.GlobalGatewayLogFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;

import java.util.stream.Collectors;

/**
 * @author SUNNY
 * @description: 网关配置类
 * @create: 2022-07-01 14:09
 */
@Slf4j
@Configuration
public class GatewayConfig {

    /**
     * 路由配置
     * @param builder
     * @return
     */
    @Bean
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        log.info("路由配置");
        return builder.routes().route(r -> r.path("/user/**").uri("http://localhost:8060")).build();
    }

    /**
     * 请求认证鉴权过滤器
     * @return
     */
    @Bean
    public GlobalGatewayAuthFilter globalGatewayAuthFilter() {
        return new GlobalGatewayAuthFilter();
    }

    /**
     * 请求日志过滤器
     * @return
     */
    @Bean
    public GlobalGatewayLogFilter globalGatewayLogFilter() {
        return new GlobalGatewayLogFilter();
    }

    @Bean
    @ConditionalOnMissingBean
    public HttpMessageConverters messageConverters(ObjectProvider<HttpMessageConverter<?>> converters) {
        return new HttpMessageConverters(converters.orderedStream().collect(Collectors.toList()));
    }

}
