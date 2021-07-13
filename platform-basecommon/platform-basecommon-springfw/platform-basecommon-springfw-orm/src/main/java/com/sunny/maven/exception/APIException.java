package com.sunny.maven.exception;

import lombok.Getter;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/7/29 23:58
 * @description：自定义异常类
 * @modified By：
 * @version: 1.0.0
 */
@Getter
public class ApiException extends RuntimeException {
    private int code;
    private String msg;

    public ApiException() {
        this(530, "接口错误");
    }

    public ApiException(String msg) {
        this(530, msg);
    }

    public ApiException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
