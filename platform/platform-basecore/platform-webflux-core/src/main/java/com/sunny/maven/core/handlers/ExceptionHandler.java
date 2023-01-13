package com.sunny.maven.core.handlers;

import com.sunny.maven.core.exception.CheckException;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebExceptionHandler;
import reactor.core.publisher.Mono;

/**
 * @author SUNNY
 * @description: 异常处理
 * @create: 2022-09-29 17:22
 */
@Component
@Order(-2) // 设置优先级，最少设置-2，否者不能工作
public class ExceptionHandler implements WebExceptionHandler {
    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.BAD_REQUEST);
        response.getHeaders().setContentType(MediaType.TEXT_PLAIN);
        String errMsg = toStr(ex);
        DataBuffer db = response.bufferFactory().wrap(errMsg.getBytes());
        return response.writeWith(Mono.just(db));
    }

    private String toStr(Throwable ex) {
        if (ex instanceof CheckException) {
            CheckException e = (CheckException) ex;
            return e.getFieldName() + ":不合法的值->" + e.getFieldValue();
        } else {
            ex.printStackTrace();
            return ex.toString();
        }
    }
}
