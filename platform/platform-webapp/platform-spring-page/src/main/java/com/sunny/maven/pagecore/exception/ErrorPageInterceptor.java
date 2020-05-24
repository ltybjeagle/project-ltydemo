package com.sunny.maven.pagecore.exception;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/5/10 17:32
 * @description：自定义errorPage拦截器
 * @modified By：
 * @version: 1.0.0
 */
@Component
public class ErrorPageInterceptor extends HandlerInterceptorAdapter {
    private List<Integer> errorCodeList = Arrays.asList(404, 500);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if (errorCodeList.contains(response.getStatus())) {
            String errorPage = "/exception/" + response.getStatus() + ".jsp";
            response.setStatus(HttpServletResponse.SC_OK);
            request.getRequestDispatcher(errorPage).forward(request, response);
        }
        return super.preHandle(request, response, handler);
    }
}
