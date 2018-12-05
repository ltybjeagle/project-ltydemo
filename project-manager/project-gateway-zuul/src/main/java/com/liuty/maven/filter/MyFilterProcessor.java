package com.liuty.maven.filter;

import com.netflix.zuul.FilterProcessor;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

/**
 * 扩展过滤执行器
 */
public class MyFilterProcessor extends FilterProcessor {

    /**
     * 覆写过滤器执行方法，拦截异常，将异常过滤器注入上下文
     * @param zuulFilter
     * @return
     * @throws ZuulException
     */
    @Override
    public Object processZuulFilter(ZuulFilter zuulFilter) throws ZuulException {
        try {
            return super.processZuulFilter(zuulFilter);
        } catch (ZuulException ex) {
            RequestContext ctx = RequestContext.getCurrentContext();
            ctx.set("failed.filter", zuulFilter);
            throw ex;
        }
    }
}
