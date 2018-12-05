package com.liuty.maven.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.springframework.cloud.netflix.zuul.filters.post.SendErrorFilter;
import org.springframework.stereotype.Component;

/**
 * Post过滤器处理的结果不能被SendErrorFilter处理，通过扩展SendErrorFilter来处理Post过滤器异常情况
 */
@Component
public class ErrorExtFilter extends SendErrorFilter {

    @Override
    public String filterType() {
        return "error";
    }

    @Override
    public int filterOrder() {
        return 30;
    }

    /**
     * 只处理Post过滤器
     * @return
     */
    @Override
    public boolean shouldFilter() {
        RequestContext ctx = RequestContext.getCurrentContext();
        ZuulFilter zuulFilter = (ZuulFilter) ctx.get("failed.filter");
        if (zuulFilter != null && zuulFilter.filterType().equals("post")) {
            return true;
        }
        return false;
    }
}
