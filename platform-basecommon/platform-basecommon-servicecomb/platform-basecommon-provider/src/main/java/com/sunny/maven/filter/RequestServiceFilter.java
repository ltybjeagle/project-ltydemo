package com.sunny.maven.filter;

import org.apache.servicecomb.common.rest.filter.HttpServerFilter;
import org.apache.servicecomb.core.Invocation;
import org.apache.servicecomb.foundation.vertx.http.HttpServletRequestEx;
import org.apache.servicecomb.foundation.vertx.http.HttpServletResponseEx;
import org.apache.servicecomb.swagger.invocation.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author SUNNY
 * @description: 请求过滤器
 * @create: 2021-08-24 21:42
 */
public class RequestServiceFilter implements HttpServerFilter {
    private static final Logger logger = LoggerFactory.getLogger(RequestServiceFilter.class);
    @Override
    public int getOrder() {
        return 0;
    }

    @Override
    public Response afterReceiveRequest(Invocation invocation, HttpServletRequestEx requestEx) {
        String threadName = Thread.currentThread().getName();
        logger.info("filter:  provider接收middle请求, 线程：{}", threadName);
        return null;
    }

    @Override
    public void beforeSendResponse(Invocation invocation, HttpServletResponseEx responseEx) {
        String threadName = Thread.currentThread().getName();
        logger.info("filter:  provider响应middle请求, 线程：{}", threadName);
    }
}
