package com.sunny.maven.enums;

/**
 * @author SUNNY
 * @description: 结果编码枚举
 * @create: 2021-08-11 15:06
 */
public enum ResultCodeEnum {
    /**
     * 请求参数校验失败
     */
    PARAM_ILLEGAL("5001")
    ;

    /**
     * 结果编码
     */
    private String resultCode;

    /**
     * 构造结果编码
     * @param resultCode 结果编码
     */
    ResultCodeEnum(String resultCode) {
        this.resultCode = resultCode;
    }

    /**
     * 获取结果编码
     * @return 结果编码
     */
    public String getResultCode() {
        return resultCode;
    }

}
