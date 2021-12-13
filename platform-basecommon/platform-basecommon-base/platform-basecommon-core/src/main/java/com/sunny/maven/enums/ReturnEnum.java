package com.sunny.maven.enums;

/**
 * @author SUNNY
 * @description: 响应码枚举类
 * @create: 2021-11-17 10:43
 */
public enum ReturnEnum {
    /**
     * 成功
     */
    SUCCESS("10000", "成功", "ok"),
    /**
     * 未知错误
     */
    UNKNOWN_ERROR("-1", "未知错误", "unknown error"),
    /**
     * 二级宏观错误码，用户传参相关错误
     */
    A_PARAM_VALIDATION_ERROR("A0100", "参数校验失败", "parameter validation error");

    /**
     * 构造结果编码
     * @param msgCode 编码
     * @param msgCn 中文说明
     * @param msgEn 英文说明
     */
    ReturnEnum(String msgCode, String msgCn, String msgEn) {
        this.msgCode = msgCode;
        this.msgCn = msgCn;
        this.msgEn = msgEn;
    }

    /**
     * 编码
     */
    private String msgCode;
    /**
     * 中文说明
     */
    private String msgCn;
    /**
     * 英文说明
     */
    private String msgEn;

    /**
     * 编码
     * @return String
     */
    public String getMsgCode() {
        return msgCode;
    }

    /**
     * 中文说明
     * @return String
     */
    public String getMsgCn() {
        return msgCn;
    }

    /**
     * 英文说明
     * @return String
     */
    public String getMsgEn() {
        return msgEn;
    }
}
