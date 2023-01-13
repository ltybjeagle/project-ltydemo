package com.sunny.maven.core.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author SUNNY
 * @description: 自定义异步调用拦截器
 * @create: 2022-09-24 15:52
 */
@Slf4j
public class PlatFormAsyncHandlerInterceptor implements AsyncHandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        log.info("{}进入postHandle方法", Thread.currentThread().getName());
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        log.info("{}进入afterCompletion方法", Thread.currentThread().getName());
        if (null != ex) {
            log.info("发生异常: {}", ex.getMessage());
        }
    }

    @Override
    public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        log.info("{}进入afterConcurrentHandlingStarted方法", Thread.currentThread().getName());
    }
}
