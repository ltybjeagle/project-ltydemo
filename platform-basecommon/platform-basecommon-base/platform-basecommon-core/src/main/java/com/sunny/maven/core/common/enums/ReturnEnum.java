package com.sunny.maven.core.common.enums;

/**
 * @author SUNNY
 * @description: 响应码枚举类
 * @create: 2021-11-17 10:43
 */
public enum ReturnEnum {
    /**
     * 200 成功
     */
    SUCCESS(200, "成功", "ok"),
    /**
     * Aes 590 code enum.
     */
    AES590(590, "AES加密失败", "AES encrypt fail"),
    /**
     * Aes 591 code enum.
     */
    AES591(591, "AES解密失败", "AES decrypt fail"),
    /**
     * 500 未知错误
     */
    UNKNOWN_ERROR(500, "未知错误", "unknown error"),
    /**
     * 二级宏观错误码，用户传参相关错误
     */
    A_PARAM_VALIDATION_ERROR(510, "参数校验失败", "parameter validation error"),
    /**
     * 1001 用户中心
     */
    ////////////////////////////////////////////////////
    /**
     * Uac 501 error code enum.
     */
    UAC501(501, "TOKEN解析失败", "TOKEN parse fail"),
    /**
     * Uac 502 error code enum.
     */
    UAC502(502, "验证token失败", "check TOKEN fail");

    /**
     * Gets enum.
     * @param msgCode the code
     * @return the enum
     */
    public static ReturnEnum getEnum(int msgCode) {
        for (ReturnEnum ele : ReturnEnum.values()) {
            if (ele.getMsgCode() == msgCode) {
                return ele;
            }
        }
        return null;
    }

    /**
     * 构造结果编码
     * @param msgCode 编码
     * @param msgCn 中文说明
     * @param msgEn 英文说明
     */
    ReturnEnum(int msgCode, String msgCn, String msgEn) {
        this.msgCode = msgCode;
        this.msgCn = msgCn;
        this.msgEn = msgEn;
    }

    /**
     * 编码
     */
    private int msgCode;
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
    public int getMsgCode() {
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
