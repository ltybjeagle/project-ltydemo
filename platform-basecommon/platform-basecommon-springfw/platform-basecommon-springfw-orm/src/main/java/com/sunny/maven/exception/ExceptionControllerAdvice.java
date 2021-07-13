package com.sunny.maven.exception;

import com.sunny.maven.enums.ResultCode;
import com.sunny.maven.vo.ResultVO;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/7/29 23:55
 * @description：统一异常处理
 * @modified By：
 * @version: 1.0.0
 */
@RestControllerAdvice
public class ExceptionControllerAdvice {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResultVO<String> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);
        // 然后提取错误提示信息进行返回
        return new ResultVO<>(ResultCode.FAILED, objectError.getDefaultMessage());
    }

    @ExceptionHandler(ApiException.class)
    public ResultVO<String> APIExceptionHandler(ApiException e) {
        // 注意哦，这里返回类型是自定义响应体
        return new ResultVO<>(ResultCode.VALIDATE_FAILED, e.getMsg());
    }
}
