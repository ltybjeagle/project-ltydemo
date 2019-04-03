package com.liuty.maven.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 网关过滤器，校验请求合法性
 */
public class AccessFilter extends ZuulFilter {
    private static final Logger logger = LoggerFactory.getLogger(AccessFilter.class);
    /**
     * 请求前校验
     * @return
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 多过滤器执行顺序
     * @return
     */
    @Override
    public int filterOrder() {
        return 0;
    }

    /**
     * 校验全部请求
     * @return
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 校验逻辑
     * @return
     */
    @Override
    public Object run() {
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        logger.info("send {} request to {}", request.getMethod(), request.getRequestURI().toString());
        Object accessToken = request.getParameter("accessToken");
        if (accessToken == null) {
            logger.warn("access token is empty");
            requestContext.setSendZuulResponse(false);
            requestContext.setResponseStatusCode(401);
            return null;
        }
        logger.info("access token ok");
        return null;
    }
}
