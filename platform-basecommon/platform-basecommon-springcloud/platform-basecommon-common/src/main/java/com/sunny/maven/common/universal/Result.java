package com.sunny.maven.common.universal;

import com.sunny.maven.enums.ResultCodeEnum;

/**
 * @author SUNNY
 * @description: Rest结果对象
 * @create: 2021-08-11 11:44
 */
public class Result<T> {
    /**
     * 响应状态：true：成功、false：失败
     */
    private Boolean success;
    /**
     * 响应数据
     */
    private T data;
    /**
     * 响应描述
     */
    private String message;
    /**
     * 响应编码
     */
    private String code;

    /**
     * 私有构造函数
     */
    private Result() {
    }

    /**
     * 构造结果对象
     * @param codeEnum 编码
     * @param <T> 结果类型
     * @return 结果对象
     */
    public static <T> Result<T> buildErrorResult(ResultCodeEnum codeEnum) {
        Result<T> result = new Result<>();
        result.setCode(codeEnum.getResultCode());
        result.setSuccess(false);
        return result;
    }

    /**
     * 获取结果状态
     * @return 状态：true/false
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     * 设置结果状态
     * @param success 状态：true/false
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    /**
     * 获取结果数据
     * @return T
     */
    public T getData() {
        return data;
    }

    /**
     * 设置结果数据
     * @param data 数据
     */
    public void setData(T data) {
        this.data = data;
    }

    /**
     * 获取结果描述
     * @return 描述信息
     */
    public String getMessage() {
        return message;
    }

    /**
     * 设置结果描述
     * @param message 描述信息
     */
    public void setMessage(String message) {
        this.message = message;
    }

    /**
     * 获取结果编码
     * @return 编码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置结果编码
     * @param code 编码
     */
    public void setCode(String code) {
        this.code = code;
    }
}
