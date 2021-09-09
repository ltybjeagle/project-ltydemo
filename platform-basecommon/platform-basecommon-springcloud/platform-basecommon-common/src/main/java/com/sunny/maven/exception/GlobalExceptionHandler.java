package com.sunny.maven.exception;

import com.sunny.maven.common.universal.Result;
import com.sunny.maven.enums.ResultCodeEnum;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.text.MessageFormat;
import java.util.List;
import java.util.Set;

/**
 * @author SUNNY
 * @description: Rest控制器全局异常处理
 * @create: 2021-08-11 11:28
 */
@RestControllerAdvice(basePackages = {"com.sunny.maven"})
public class GlobalExceptionHandler {
    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 参数校验异常：缺少参数
     * @param e 缺少参数异常
     * @return Result
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    @ResponseBody
    public Result<Object> handleException(MissingServletRequestParameterException e) {
        String msg = MessageFormat.format("缺少参数{0}", e.getParameterName());
        logger.error(msg);
        Result<Object> result = Result.buildErrorResult(ResultCodeEnum.PARAM_ILLEGAL);
        result.setMessage(msg);
        return result;
    }

    /**
     * 参数校验异常：单参数校验失败
     * @param e 单参数校验失败异常
     * @return Result
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public Result<Object> handleException(ConstraintViolationException e) {
        Result<Object> result = Result.buildErrorResult(ResultCodeEnum.PARAM_ILLEGAL);
        Set<ConstraintViolation<?>> sets = e.getConstraintViolations();
        if (CollectionUtils.isNotEmpty(sets)) {
            StringBuilder str = new StringBuilder();
            sets.forEach(error -> {
                if (error instanceof FieldError) {
                    str.append(((FieldError) error).getField()).append(":");
                }
                str.append(error.getMessage()).append(";");
            });
            String msg = str.toString();
            msg = StringUtils.substring(msg, 0, msg.length() - 1);
            result.setMessage(msg);
        }
        return result;
    }

    /**
     * 参数校验异常：get请求的对象参数校验失败
     * @param e 参数校验失败异常
     * @return Result
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Result<Object> handleException(BindException e) {
        return validHandleException(e);
    }

    /**
     * 参数校验异常：post请求的对象参数校验失败
     * @param e 参数校验失败异常
     * @return Result
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Result<Object> handleException(MethodArgumentNotValidException e) {
        return validHandleException(e);
    }

    /**
     * get/post请求的对象参数校验失败
     * @param e 参数校验失败异常
     * @return Result
     */
    private Result<Object> validHandleException(Exception e) {
        Result<Object> result = Result.buildErrorResult(ResultCodeEnum.PARAM_ILLEGAL);
        List<ObjectError> errors = null;
        if (e instanceof BindException) {
            errors = ((BindException) e).getBindingResult().getAllErrors();
        }
        if (e instanceof MethodArgumentNotValidException) {
            errors = ((MethodArgumentNotValidException) e).getBindingResult().getAllErrors();
        }
        String msg = getValidExceptionMsg(errors);
        if (StringUtils.isNotBlank(msg)) {
            result.setMessage(msg);
        }
        return result;
    }

    /**
     * 异常字段处理
     * @param errors 异常字段列表
     * @return String
     */
    private String getValidExceptionMsg(List<ObjectError> errors) {
        if (CollectionUtils.isNotEmpty(errors)) {
            StringBuilder str = new StringBuilder();
            errors.forEach(error -> {
                if (error instanceof FieldError) {
                    str.append(((FieldError) error).getField()).append(":");
                }
                str.append(error.getDefaultMessage()).append(";");
            });
            String msg = str.toString();
            msg = StringUtils.substring(msg, 0, msg.length() - 1);
            return msg;
        }
        return null;
    }
}
