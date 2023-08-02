package com.sunny.maven.middle.springmvc.filter;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * @author SUNNY
 * @description:
 * @create: 2023/7/21 17:23
 */
@Slf4j
@WebFilter
public class MiddleFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("MiddleFilter>>>init");
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        log.info("MiddleFilter>>>doFilter");
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        log.info("MiddleFilter>>>destroy");
    }
}
