package com.sunny.maven.gateway.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import reactor.core.publisher.Mono;

/**
 * @author SUNNY
 * @description: 网关过滤器配置
 * @create: 2022-07-05 12:05
 */
@Configuration
@Slf4j
public class GatewayFilterConfig {

    @Bean
    @Order(-1)
    public GlobalFilter globalFilter() {
        log.info("执行前置过滤器逻辑");
        return (exchange, chain) -> {
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                log.info("执行后置过滤器逻辑");
            }));
        };
    }
}
