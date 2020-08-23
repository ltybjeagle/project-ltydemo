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
public class APIException extends RuntimeException {
    private int code;
    private String msg;

    public APIException() {
        this(1001, "接口错误");
    }

    public APIException(String msg) {
        this(1001, msg);
    }

    public APIException(int code, String msg) {
        super(msg);
        this.code = code;
        this.msg = msg;
    }
}
