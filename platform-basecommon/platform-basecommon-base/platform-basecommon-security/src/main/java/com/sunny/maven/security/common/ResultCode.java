package com.sunny.maven.security.common;

/**
 * @author SUNNY
 * @description: 统一返回接口实现类
 * @create: 2022-04-13 13:49
 */
public enum ResultCode implements IResultCode {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 业务异常
     */
    FAILURE(400, "业务异常"),
    /**
     * 服务异常
     */
    ERROR(500, "服务异常"),
    /**
     * 参数错误
     */
    GLOBAL_PARAM_ERROR(540, "参数错误");

    /**
     * 构造函数
     * @param code 状态码
     * @param msg 消息内容
     */
    ResultCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    /**
     * 状态码
     */
    final int code;
    /**
     * 消息内容
     */
    final String msg;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
