package com.sunny.maven.core.fluxclient.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpMethod;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author SUNNY
 * @description: 方法调用信息类
 * @create: 2022-09-29 18:12
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MethodInfo {
    /**
     * 请求url
     */
    private String url;
    /**
     * 请求方法
     */
    private HttpMethod method;
    /**
     * 请求参数（url）
     */
    private Map<String, Object> params;
    /**
     * 请求body
     */
    private Mono<?> body;
    /**
     * 请求body的类型
     */
    private Class<?> bodyElementType;
    /**
     * 返回flux还是Mono
     */
    private boolean returnFlux;
    /**
     * 返回对象的类型
     */
    private Class<?> returnElementType;
}
