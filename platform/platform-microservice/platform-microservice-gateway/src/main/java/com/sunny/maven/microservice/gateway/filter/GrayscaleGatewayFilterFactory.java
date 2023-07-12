package com.sunny.maven.microservice.gateway.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author SUNNY
 * @description: 自定义过滤器模拟实现灰度发布
 * @create: 2023/7/12 18:40
 */
@Component
public class GrayscaleGatewayFilterFactory
        extends AbstractGatewayFilterFactory<GrayscaleGatewayFilterConfig> {
    @Override
    public GatewayFilter apply(GrayscaleGatewayFilterConfig config) {
        return (exchange, chain) -> {
            if (config.isGrayscale()) {
                System.out.println("开启了灰度发布功能...");
            } else {
                System.out.println("关闭了灰度发布功能...");
            }
            return chain.filter(exchange);
        };
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return List.of("grayscale");
    }

    public GrayscaleGatewayFilterFactory() {
        super(GrayscaleGatewayFilterConfig.class);
    }
}
