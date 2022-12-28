package com.sunny.maven.core.common.rstenum;

import com.sunny.maven.core.common.constants.HttpCode;
import lombok.Getter;

/**
 * @author SUNNY
 * @description: 统一响应状态
 * @create: 2022-07-25 13:47
 */
@Getter
public enum  ResultCodeEnum {
    SUCCESS(true, HttpCode.SUCCESS, "执行成功!"),
    UNKNOWN_ERROR(false, HttpCode.FAILURE, "未知错误!"),
    TOKEN_INVALID(false, HttpCode.FAILURE, "认证已经失效!"),
    TOKEN_MISS(false, HttpCode.FAILURE, "未提供认证Token!"),
    USER_SESSION_LOSE(false, HttpCode.FAILURE, "用户会话信息丢失!");


    /**
     * 响应是否成功
     */
    private Boolean success;
    /**
     * 响应状态码
     */
    private Integer code;
    /**
     * 响应信息
     */
    private String codeMsg;

    /**
     * 构造函数
     * @param success 响应是否成功
     * @param code 响应状态码
     * @param codeMsg 响应信息
     */
    ResultCodeEnum(Boolean success, Integer code, String codeMsg) {
        this.success = success;
        this.code = code;
        this.codeMsg = codeMsg;
    }
}
