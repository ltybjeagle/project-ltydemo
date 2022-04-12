package com.sunny.maven.security.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SUNNY
 * @description: 验证码校验过滤器
 * @create: 2022-02-08 18:21
 */
public class Oauth2VerificationCodeFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(Oauth2VerificationCodeFilter.class);

    /**
     * 验证码过滤器
     * @param request
     * @param response
     * @param filterChain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        logger.info("********Oauth2VerificationCodeFilter**********");
        filterChain.doFilter(request, response);
    }
}
