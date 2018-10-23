package com.liuty.maven.handler;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * 控制器异常处理
 */
@ControllerAdvice
public class ControllerExceptionHandler {

    /**
     * 控制器抛出Exception异常，处理
     * @return
     */
    @ExceptionHandler(Exception.class)
    public String exceptionHandler() {
        return "error/exception";
    }
}
