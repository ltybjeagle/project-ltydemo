package com.sunny.maven.core.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SUNNY
 * @description: 业务请求过滤器
 * @create: 2022-09-24 12:42
 */
@Slf4j
@WebFilter(filterName = "GRCSFilter", urlPatterns = "/*")
public class GlobalRequestContextServletFilter extends OncePerRequestFilter {

    /**
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        log.info("GlobalRequestContextServletFilter过滤器");
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
