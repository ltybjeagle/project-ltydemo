package com.sunny.maven.core.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

/**
 * @author SUNNY
 * @description: 请求过滤器
 * @create: 2022-10-11 14:41
 */
@Slf4j
public class GlobalRequestContextFilter implements WebFilter {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        String reqPath = exchange.getRequest().getPath().toString();
        if (reqPath.equalsIgnoreCase("/favicon.ico")) {
            return exchange.getResponse().writeWith(Mono.justOrEmpty(null));
        }
        log.info("请求：{}", reqPath);
        return chain.filter(exchange);
    }
}
