package com.sunny.maven.core.common.resp;

import com.sunny.maven.core.common.resp.rstenum.ResultCodeEnum;
import lombok.Data;

import java.io.Serializable;

/**
 * @author SUNNY
 * @description: 返回的结果数据
 * @create: 2022-07-25 14:22
 */
@Data
public class R<T> implements Serializable {
    private static final long serialVersionUID = -9058827414329088624L;
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
     * 返回的数据
     */
    private T data;

    /**
     * 通用返回成功
     * @return R
     */
    public static R ok() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.SUCCESS.getSuccess());
        r.setCode(ResultCodeEnum.SUCCESS.getCode());
        r.setCodeMsg(ResultCodeEnum.SUCCESS.getCodeMsg());
        return r;
    }

    /**
     * 通用返回失败，未知错误
     * @return R
     */
    public static R error() {
        R r = new R();
        r.setSuccess(ResultCodeEnum.UNKNOWN_ERROR.getSuccess());
        r.setCode(ResultCodeEnum.UNKNOWN_ERROR.getCode());
        r.setCodeMsg(ResultCodeEnum.UNKNOWN_ERROR.getCodeMsg());
        return r;
    }

    /**
     * 设置结果，形参为结果枚举
     * @param result ResultCodeEnum
     * @return R
     */
    public static R setResult(ResultCodeEnum result) {
        R r = new R();
        r.setSuccess(result.getSuccess());
        r.setCode(result.getCode());
        r.setCodeMsg(result.getCodeMsg());
        return r;
    }

    /**------------使用链式编程，返回类本身-----------**/
    /**
     * 自定义返回数据
     * @param data 数据
     * @return R
     */
    public R data(T data) {
        this.setData(data);
        return this;
    }

    /**
     * 自定义状态信息
     * @param message 响应信息
     * @return R
     */
    public R message(String message) {
        this.setCodeMsg(message);
        return this;
    }

    /**
     * 自定义状态码
     * @param code 响应状态码
     * @return R
     */
    public R code(Integer code) {
        this.setCode(code);
        return this;
    }

    /**
     * 自定义返回结果
     * @param success 响应是否成功
     * @return R
     */
    public R success(Boolean success) {
        this.setSuccess(success);
        return this;
    }

    /**
     * 私有构造器
     */
    private R() {}
}
