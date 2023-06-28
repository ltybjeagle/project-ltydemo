package com.sunny.maven.core.handle;

import com.sunny.maven.core.common.resp.R;
import com.sunny.maven.core.common.resp.rstenum.ResultCodeEnum;
import com.sunny.maven.core.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author SUNNY
 * @description: 全局异常处理器
 * @create: 2022-06-16 12:26
 */
@RestControllerAdvice
@ResponseBody
public class RestExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(RestExceptionHandler.class);

    /**
     * 空指针异常
     * @param e 异常
     * @return R
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public R<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        logger.error("请求参数校验异常: {}", objectError.getDefaultMessage());
        return R.setResult(ResultCodeEnum.VALIDATE_FAILED).message(objectError.getDefaultMessage());
    }

    /**
     * 空指针异常
     * @param e 异常
     * @return R
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public R<?> handleNullPointerException(NullPointerException e) {
        logger.error("服务器抛出了空指针异常: {}", e.getMessage());
        return R.error().message(e.getMessage());
    }

    /**
     * 自定义定异常处理方法
     * @param e 异常
     * @return R
     */
    @ExceptionHandler(AppException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public R<?> handleAppException(AppException e) {
        logger.error("服务器抛出了自定义异常: {}", e.getMessage());
        return R.error().message(e.getMessage()).code(e.getCode());
    }

    /**
     * 全局异常处理，统一返回状态码
     * @param e 异常
     * @return R
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public R<?> handleException(Exception e) {
        logger.error("服务器抛出了异常: {}", e.getMessage());
        return R.error().message(e.getMessage());
    }

    /**
     * 全局异常处理，统一返回状态码
     * @param e 异常
     * @return R
     */
    @ExceptionHandler(Throwable.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public R<?> handleThrowable(Throwable e) {
        logger.error("服务器抛出了异常: {}", e.getMessage());
        return R.error().message(e.getMessage());
    }
}
