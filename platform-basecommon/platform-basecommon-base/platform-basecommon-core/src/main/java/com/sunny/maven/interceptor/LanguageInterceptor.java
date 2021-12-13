package com.sunny.maven.interceptor;

import com.sunny.maven.holder.RequestHolderUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author SUNNY
 * @description: 类型拦截器
 * @create: 2021-11-17 14:05
 */
public class LanguageInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String acceptLanguage = request.getHeader(HttpHeaders.ACCEPT_LANGUAGE);
        RequestHolderUtil.addLang(acceptLanguage);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
                                @Nullable Exception ex) throws Exception {
        RequestHolderUtil.remove();
    }
}
