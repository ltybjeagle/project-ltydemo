package com.sunny.maven.core.handle;

import com.alibaba.fastjson.JSON;
import com.sunny.maven.core.annotation.common.NotControllerResponseAdvice;
import com.sunny.maven.core.common.resp.R;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

/**
 * @author SUNNY
 * @description: 统一包装响应
 * @create: 2022-07-01 18:42
 */
@RestControllerAdvice(basePackages = {"com.sunny.maven"})
public class RestResponseAdvice implements ResponseBodyAdvice<Object> {
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        // response是 R 类型，或者注释了NotControllerResponseAdvice都不进行包装
        return !returnType.getParameterType().isAssignableFrom(R.class)
                && !returnType.hasMethodAnnotation(NotControllerResponseAdvice.class);
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        // String类型不能直接包装
        if (returnType.getGenericParameterType().equals(String.class)) {
            // 将数据包装在 R 里后转换为json串进行返回
            return JSON.toJSONString(R.ok().data(body));
        }
        // 否则直接包装成 R 返回
        return R.ok().data(body);
    }
}
