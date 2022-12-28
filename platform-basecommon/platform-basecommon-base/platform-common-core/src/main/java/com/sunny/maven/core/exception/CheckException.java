package com.sunny.maven.core.exception;

import lombok.Data;

/**
 * @author SUNNY
 * @description: 自定义扩展校验异常
 * @create: 2022-09-29 15:54
 */
@Data
public class CheckException extends RuntimeException {
    /**
     * 出错字段的名称
     */
    private String fieldName;
    /**
     * 出错字段的值
     */
    private String fieldValue;

    public CheckException() {
        super();
    }
    public CheckException(String message) {
        super(message);
    }
    public CheckException(String message, Throwable cause) {
        super(message, cause);
    }
    public CheckException(Throwable cause) {
        super(cause);
    }
    protected CheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
    public CheckException(String fieldName, String fieldValue) {
        super();
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }
}
