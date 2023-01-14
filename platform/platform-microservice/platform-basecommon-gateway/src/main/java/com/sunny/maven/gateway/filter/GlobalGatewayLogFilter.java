package com.sunny.maven.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author SUNNY
 * @description: 自定义全局过滤器，模拟实现获取客户端信息并统计接口访问时长
 * @create: 2022-07-05 12:23
 */
@Slf4j
public class GlobalGatewayLogFilter implements GlobalFilter, Ordered {
    /**
     * 开始访问时间
     */
    private static final String BEGIN_VISIT_TIME = "begin_visit_time";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        log.info("请求URL={}", serverHttpRequest.getURI().toString());
        // 先记录下访问接口的开始时间
        exchange.getAttributes().put(BEGIN_VISIT_TIME, System.currentTimeMillis());
        return chain.filter(exchange).then(Mono.fromRunnable(() -> {
            Long beginVisitTime = exchange.getAttribute(BEGIN_VISIT_TIME);
            if (beginVisitTime != null) {
                log.info("访问接口主机: {}", serverHttpRequest.getURI().getHost());
                log.info("访问接口端口: {}", serverHttpRequest.getURI().getPort());
                log.info("访问接口URL: {}", serverHttpRequest.getURI().getPath());
                log.info("访问接口URL参数: {}", serverHttpRequest.getURI().getRawQuery());
                log.info("客户端地址: {}", serverHttpRequest.getHeaders().getFirst("x-forwarded-for"));
                log.info("访问接口时长: {}", (System.currentTimeMillis() - beginVisitTime) + "ms");
            }
        }));
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
