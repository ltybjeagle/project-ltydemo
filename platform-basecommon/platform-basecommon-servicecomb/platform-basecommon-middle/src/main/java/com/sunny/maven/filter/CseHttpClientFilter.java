package com.sunny.maven.filter;

import org.apache.servicecomb.common.rest.filter.HttpClientFilter;
import org.apache.servicecomb.core.Invocation;
import org.apache.servicecomb.foundation.vertx.http.HttpServletRequestEx;
import org.apache.servicecomb.foundation.vertx.http.HttpServletResponseEx;
import org.apache.servicecomb.swagger.invocation.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SUNNY
 * @description: 客户端请求过滤器
 * @create: 2021-08-31 17:44
 */
public class CseHttpClientFilter implements HttpClientFilter {
    private static final Logger logger = LoggerFactory.getLogger(CseHttpClientFilter.class);
    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public void beforeSendRequest(Invocation invocation, HttpServletRequestEx requestEx) {
        String threadName = Thread.currentThread().getName();
        logger.info("filter:  middle请求provider, 线程：{}", threadName);
    }

    @Override
    public Response afterReceiveResponse(Invocation invocation, HttpServletResponseEx responseEx) {
        String threadName = Thread.currentThread().getName();
        logger.info("filter:  middle请求provider, 线程：{}, 状态：{}", threadName, responseEx.getStatus());
        return null;
    }
}
