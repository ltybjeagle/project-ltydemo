package com.sunny.maven.constant;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * @author SUNNY
 * @description: 错误异常定义
 * @create: 2021-08-21 19:33
 */
public enum ErrorEnum {
    /**
     * 数据库异常
     */
    OPERATION_EXCEPTION("500", "操作失败!"),
    DATABASE_EXCEPTION("510", "数据库操作异常!"),
    WORKFLOW_EXCEPTION("520", "工作流操作异常!"),
    CACHE_EXCEPTION("530", "缓存操作异常!"),
    CSE_EXCEPTION("540", "服务接口调用异常!")
    ;

    /**
     * 构造函数
     * @param code 异常编码
     * @param message 异常描述
     */
    ErrorEnum(String code, String message) {
        this.code = code;
        this.message = message;
        this.advise = DEFAULT_ADVISE;
    }
    /**
     * 构造函数
     * @param code 异常编码
     * @param message 异常描述
     * @param advise 处理意见
     */
    ErrorEnum(String code, String message, String advise) {
        this.code = code;
        this.message = message;
        this.advise = advise;
    }
    /**
     * 默认异常编码
     */
    private static final String DEFAULT_ADVISE = "请联系管理员!";
    /**
     * 异常编码
     */
    private String code;
    /**
     * 异常描述
     */
    private String message;
    /**
     * 处理建议
     */
    private String advise;
    /**
     * 获取异常编码
     * @return String
     */
    public String getCode() {
        return code;
    }
    /**
     * 获取异常编码
     * @return String
     */
    public String getMessage() {
        return message;
    }
    /**
     * 获取异常处理意见
     * @return String
     */
    public String getAdvise() {
        return advise;
    }
    /**
     * 根据编码获取异常对象
     * @param code 异常编码
     * @return ErrorConstant
     */
    public static ErrorEnum getErrorEnum(String code) {
        return Stream.of(ErrorEnum.values()).
                filter(error -> Objects.equals(code, error.getCode())).
                findAny().orElse(OPERATION_EXCEPTION);
    }
}
