package com.sunny.maven.core.advice;

import com.sunny.maven.core.exception.CheckException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

/**
 * @author SUNNY
 * @description: 异常处理切面
 * @create: 2022-09-29 15:37
 */
@ControllerAdvice
public class ExceptionAdvice {

    /**
     * 参数检验异常处理
     * @param e 异常
     * @return ResponseEntity
     */
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity handleBindException(WebExchangeBindException e) {
        return new ResponseEntity<>(toStr(e), HttpStatus.BAD_REQUEST);
    }

    /**
     * 参数检验异常处理
     * @param e 异常
     * @return ResponseEntity
     */
    @ExceptionHandler(CheckException.class)
    public ResponseEntity handleBindException(CheckException e) {
        return new ResponseEntity<>(toStr(e), HttpStatus.BAD_REQUEST);
    }

    /**
     * 校验异常转换成字符串
     * @param ex WebExchangeBindException
     * @return String
     */
    private String toStr(WebExchangeBindException ex) {
        return ex.getFieldErrors().stream().map(e -> e.getField() + ":" + e.getDefaultMessage()).
                reduce((s1, s2) -> s1 + "\n" + s2).orElse("");
    }

    /**
     * 校验异常转换成字符串
     * @param ex CheckException
     * @return String
     */
    private String toStr(CheckException ex) {
        return ex.getFieldName() + ":不合法的值->" + ex.getFieldValue();
    }
}
