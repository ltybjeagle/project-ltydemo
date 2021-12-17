package com.sunny.maven.facade.feign.interceptor;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SUNNY
 * @description: Feign拦截器
 * @create: 2021-12-15 17:26
 */
public class FeignRequestInterceptor implements RequestInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(FeignRequestInterceptor.class);
    /**
     * 拦截处理函数
     * @param requestTemplate 请求
     */
    @Override
    public void apply(RequestTemplate requestTemplate) {
        logger.info("Feign请求拦截处理。。。。。。");
    }

    /**
     * 构造函数
     */
    public FeignRequestInterceptor() {}
}
