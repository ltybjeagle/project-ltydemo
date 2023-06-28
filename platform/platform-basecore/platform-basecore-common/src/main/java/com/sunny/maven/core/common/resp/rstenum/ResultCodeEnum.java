package com.sunny.maven.core.common.resp.rstenum;

import com.sunny.maven.core.common.constants.CommonConstant;
import lombok.Getter;

/**
 * @author SUNNY
 * @description: 统一响应状态
 * @create: 2022-07-25 13:47
 */
@Getter
public enum ResultCodeEnum {
    SUCCESS(true, CommonConstant.SUCCESS, "执行成功!"),
    UNKNOWN_ERROR(false, CommonConstant.SERVER_FAILURE, "未知错误!"),
    TOKEN_INVALID(false, CommonConstant.SERVER_FAILURE, "认证已经失效!"),
    TOKEN_MISS(false, CommonConstant.SERVER_FAILURE, "未提供认证Token!"),
    VALIDATE_FAILED(false, CommonConstant.VALIDATE_FAILED, "参数校验失败"),
    USER_SESSION_LOSE(false, CommonConstant.SERVER_FAILURE, "用户会话信息丢失!");


    /**
     * 响应是否成功
     */
    private final Boolean success;
    /**
     * 响应状态码
     */
    private final Integer code;
    /**
     * 响应信息
     */
    private final String codeMsg;

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
