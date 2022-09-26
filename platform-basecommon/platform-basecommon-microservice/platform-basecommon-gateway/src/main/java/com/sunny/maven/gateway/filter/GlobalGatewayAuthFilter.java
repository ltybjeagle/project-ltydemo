package com.sunny.maven.gateway.filter;

import com.sunny.maven.gateway.security.AuthClient;
import com.sunny.maven.gateway.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.StopWatch;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author SUNNY
 * @description: 自定义全局过滤器，实现认证鉴权
 * @create: 2022-09-22 18:06
 */
@Slf4j
public class GlobalGatewayAuthFilter implements GlobalFilter, Ordered {
    @Autowired
    private AuthClient authClient;
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("认证鉴权过滤器");
        StopWatch exeTime = new StopWatch();
        exeTime.start();
        ServerHttpRequest serverHttpRequest = exchange.getRequest();
        String reqPath = serverHttpRequest.getURI().getPath();
        if (!reqPath.contains("/user/auth/")) {
            String tokenId = serverHttpRequest.getHeaders().getFirst("token");
            log.info("开始认证tokenId={}", tokenId);
            if (!StringUtils.hasText(tokenId)) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            String verifyResult = authClient.verifyToken(tokenId);
            if (Optional.ofNullable(verifyResult).isEmpty()) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
            //String userInfo = JsonUtils.getSubJson(verifyResult, "data");
            log.info("认证结果：{}", verifyResult);
            serverHttpRequest.mutate().header("userInfo", verifyResult).build();
        } else {
            log.info("/user/auth/认证请求，不需要认证");
        }
        exeTime.stop();
        log.info("认证耗时：{}ms", exeTime.getTotalTimeMillis());
        exchange.mutate().request(serverHttpRequest).build();
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        return 1;
    }
}
