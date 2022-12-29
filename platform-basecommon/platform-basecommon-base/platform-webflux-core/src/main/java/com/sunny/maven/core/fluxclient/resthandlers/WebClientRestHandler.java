package com.sunny.maven.core.fluxclient.resthandlers;

import com.sunny.maven.core.fluxclient.beans.MethodInfo;
import com.sunny.maven.core.fluxclient.beans.ServerInfo;
import com.sunny.maven.core.fluxclient.interfaces.RestHandler;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author SUNNY
 * @description: RestHandler实现类
 * @create: 2022-09-30 11:03
 */
public class WebClientRestHandler implements RestHandler {
    private WebClient webClient;
    /**
     * 初始化服务器信息
     * @param serverInfo
     */
    @Override
    public void init(ServerInfo serverInfo) {
        this.webClient = WebClient.create(serverInfo.getUrl());
    }

    /**
     * 调用Rest请求，返回结果
     * @param methodInfo
     * @return
     */
    @Override
    public Object invokeRest(MethodInfo methodInfo) {
        // 返回结果
        Object result = null;
        WebClient.RequestBodySpec request = this.webClient.
                // 请求方法
                method(methodInfo.getMethod()).
                        // 请求URL
                        uri(methodInfo.getUrl(), methodInfo.getParams()).
                        // 接收类型
                        accept(MediaType.APPLICATION_JSON);
        WebClient.ResponseSpec retrieve = null;
        // 判断是否带body
        if (methodInfo.getBody() != null) {
            // 发出请求
            retrieve = request.body(methodInfo.getBody(), methodInfo.getBodyElementType()).retrieve();
        } else {
            retrieve = request.retrieve();
        }
        // 处理异常
        retrieve.onStatus(httpStatus -> httpStatus.value() == 404,
                response -> Mono.just(new RuntimeException("Not Found")));
        // 处理body
        if (methodInfo.isReturnFlux()) {
            result = retrieve.bodyToFlux(methodInfo.getReturnElementType());
        } else {
            result = retrieve.bodyToMono(methodInfo.getReturnElementType());
        }
        return result;
    }
}
