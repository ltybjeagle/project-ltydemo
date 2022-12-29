package com.sunny.maven.core.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: Feign拦截器
 * @create: 2022-10-31 22:26
 */
@Component
public class FeignInterceptor implements RequestInterceptor {
    private static final String TRACE_ID = "traceId";
    @Override
    public void apply(RequestTemplate requestTemplate) {
        requestTemplate.header(TRACE_ID, (String) MDC.get(TRACE_ID));
    }
}
