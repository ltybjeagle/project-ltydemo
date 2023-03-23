package com.sunny.maven.microservice.utils.exception;

import com.sunny.maven.microservice.utils.constants.HttpCode;
import com.sunny.maven.microservice.utils.resp.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author SUNNY
 * @description: 全局异常处理器
 * @create: 2023-03-22 11:27
 */
@RestControllerAdvice
public class RestCtrlExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(RestCtrlExceptionHandler.class);

    /**
     * 全局异常处理，统一返回状态码
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        logger.error("服务器抛出了异常：{}", e.getMessage());
        return new Result<>(HttpCode.FAILURE, "执行失败", e.getMessage());
    }
}
