package com.sunny.maven.core.handle;

import com.sunny.maven.core.common.resp.R;
import com.sunny.maven.core.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author SUNNY
 * @description: 全局异常处理器
 * @create: 2022-06-16 12:26
 */
@RestControllerAdvice
public class RestExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    /**
     * 全局异常处理，统一返回状态码
     * @param e 异常
     * @return R
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R<String> handleException(Exception e) {
        logger.error("服务器抛出了异常: {}", e.getMessage());
        return R.error().message(e.getMessage());
    }

    /**
     * 空指针异常
     * @param e 异常
     * @return R
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public R error(NullPointerException e) {
        logger.error("服务器抛出了空指针异常: {}", e.getMessage());
        return R.error().message(e.getMessage());
    }

    /**
     * 自定义定异常处理方法
     * @param e 异常
     * @return R
     */
    @ExceptionHandler(AppException.class)
    @ResponseBody
    public R error(AppException e) {
        logger.error("服务器抛出了自定义异常: {}", e.getMessage());
        return R.error().message(e.getMessage()).code(e.getCode());
    }
}
