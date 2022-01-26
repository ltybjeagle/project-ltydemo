package com.sunny.maven.core.exception;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @author SUNNY
 * @description: 过滤器异常处理类
 * @create: 2022-01-17 15:52
 */
@RestController
public class FilterErrorController {

    /**
     * Filter异常控制器
     * @param request 请求对象
     */
    @RequestMapping("/error/throw")
    public void error(HttpServletRequest request) {
        AppException e = (AppException) request.getAttribute("filter.error");
        throw new AppException.AppExceptionBuilder().code(e.getCode()).message(e.getMessage()).build();
    }
}
