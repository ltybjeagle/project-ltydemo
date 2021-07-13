package com.sunny.maven.vo;

import com.sunny.maven.enums.ResultCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/7/30 0:00
 * @description：统一响应对象
 * @modified By：
 * @version: 1.0.0
 */
@Getter
@ToString
public class ResultVO<T> {
    /**
     * 状态码，比如1000代表响应成功
     */
    private Integer code;
    /**
     * 响应信息，用来说明响应情况
     */
    private String msg;

    /**
     * 响应的具体数据
     */
    private T data;

    public ResultVO(T data) {
        this(ResultCode.SUCCESS, data);
    }

    public ResultVO(ResultCode resultCode, T data) {
        this.code = resultCode.getCode();
        this.msg = resultCode.getMsg();
        this.data = data;
    }
}
