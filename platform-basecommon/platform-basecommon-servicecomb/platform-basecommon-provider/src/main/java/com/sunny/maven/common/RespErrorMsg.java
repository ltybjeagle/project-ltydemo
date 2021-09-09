package com.sunny.maven.common;

import com.sunny.maven.constant.ErrorEnum;

/**
 * @author SUNNY
 * @description: 异常响应对象
 * @create: 2021-09-02 22:07
 */
public class RespErrorMsg {
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
     * 异常堆栈
     */
    private String stackTraces;
    /**
     * 构造函数
     * @param code 异常编码
     * @param message 异常描述
     * @param advise 处理建议
     * @param stackTraces 异常堆栈
     */
    private RespErrorMsg(String code, String message, String advise, String stackTraces) {
        this.code = code;
        this.message = message;
        this.advise = advise;
        this.stackTraces = stackTraces;
    }
    /**
     * 获取异常编码
     * @return String
     */
    public String getCode() {
        return code;
    }
    /**
     * 获取异常描述
     * @return String
     */
    public String getMessage() {
        return message;
    }
    /**
     * 获取处理建议
     * @return String
     */
    public String getAdvise() {
        return advise;
    }
    /**
     * 获取异常堆栈
     * @return String
     */
    public String getStackTraces() {
        return stackTraces;
    }
    /**
     * 构造器
     */
    public static class RespErrorMsgBuilder {
        /**
         * 默认异常编码
         */
        private static final String DEFAULT_CODE = "500";
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
         * 异常堆栈
         */
        private String stackTraces;
        /**
         * 构造函数
         */
        public RespErrorMsgBuilder() {}
        /**
         * 设置异常编码
         * @param code 异常编码
         * @return RespErrorMsgBuilder
         */
        public RespErrorMsgBuilder code(String code) {
            this.code = code;
            return this;
        }
        /**
         * 设置异常描述
         * @param message 异常描述
         * @return RespErrorMsgBuilder
         */
        public RespErrorMsgBuilder message(String message) {
            this.message = message;
            return this;
        }
        /**
         * 设置处理建议
         * @param advise 处理建议
         * @return RespErrorMsgBuilder
         */
        public RespErrorMsgBuilder advise(String advise) {
            this.advise = advise;
            return this;
        }
        /**
         * 设置异常堆栈
         * @param stackTraces 异常堆栈
         * @return RespErrorMsgBuilder
         */
        public RespErrorMsgBuilder stackTraces(String stackTraces) {
            this.stackTraces = stackTraces;
            return this;
        }
        /**
         * 构造异常响应对象
         * @return
         */
        public RespErrorMsg build() {
            if (null == this.code) {
                this.code = DEFAULT_CODE;
            }
            if (null == this.message) {
                this.message = ErrorEnum.getErrorEnum(this.code).getMessage();
            }
            if (null == this.advise) {
                this.advise = ErrorEnum.getErrorEnum(this.code).getAdvise();
            }
            return new RespErrorMsg(code, message, advise, stackTraces);
        }
    }
}
