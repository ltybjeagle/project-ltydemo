package com.sunny.maven.core.filter;

import com.sunny.maven.core.exception.AppException;
import com.sunny.maven.core.util.PublicUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SUNNY
 * @description: 业务请求过滤器
 * @create: 2022-01-13 16:30
 */
@Component
@Order(-1)
@WebFilter(filterName = "BusCommonPrefixFilter", urlPatterns = "/*")
public class BusCommonPrefixFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(BusCommonPrefixFilter.class);
    private static final String KEY = "RequestId";
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
        try {
            /**
             * 设置日志标记，方便日志分析
             */
            MDC.put(KEY, PublicUtil.uuid());
            logger.info("BusCommonPrefixFilter[{}]......", httpServletRequest.getRequestURI());
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        } catch (AppException e) {
            httpServletRequest.setAttribute("filter.error", e);
            httpServletRequest.getRequestDispatcher("/error/throw").forward(httpServletRequest, httpServletResponse);
        } finally {
            MDC.remove(KEY);
        }
    }
}
