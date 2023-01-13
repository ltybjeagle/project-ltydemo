package com.sunny.maven.core.utils;

import com.sunny.maven.core.common.resp.R;
import com.sunny.maven.core.utils.json.JsonUtils;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * @author SUNNY
 * @description: WebFlux Response工具
 * @create: 2022-11-03 14:30
 */
public class ResponseUtils {

    /**
     * 输出响应信息
     * @param exchange
     * @param result
     * @param <T>
     * @return
     */
    public static <T> Mono<Void> writeWith(ServerWebExchange exchange, R<T> result) {
        ServerHttpResponse response = exchange.getResponse();
        String body = JsonUtils.toJson(result);
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(StandardCharsets.UTF_8));
        return response.writeWith(Mono.just(buffer));
    }
}
