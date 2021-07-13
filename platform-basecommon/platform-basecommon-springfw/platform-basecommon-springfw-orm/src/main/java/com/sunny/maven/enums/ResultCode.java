package com.sunny.maven.enums;

import lombok.Getter;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/7/30 0:05
 * @description：响应码
 * @modified By：
 * @version: 1.0.0
 */
@Getter
public enum ResultCode {
    SUCCESS(200, "操作成功"),
    FAILED(510, "响应失败"),
    VALIDATE_FAILED(520, "参数校验失败"),
    ERROR(500, "未知错误");

    private int code;
    private String msg;

    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
