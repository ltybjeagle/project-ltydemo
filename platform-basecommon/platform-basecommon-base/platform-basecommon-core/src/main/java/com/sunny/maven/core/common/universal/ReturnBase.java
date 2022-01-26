package com.sunny.maven.core.common.universal;

import com.sunny.maven.core.common.constant.CommonConstant;
import com.sunny.maven.core.common.context.UserInfoContextHolder;
import com.sunny.maven.core.common.enums.ReturnEnum;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author SUNNY
 * @description: 响应对象
 * @create: 2022-01-17 11:57
 */
public class ReturnBase implements Serializable {
    /**
     * 响应编码
     */
    private int code;
    /**
     * 响应描述
     */
    private String message;
    /**
     * 响应数据
     */
    private Object data;

    /**
     * 构造函数
     * @param code 编码
     * @param message 描述
     * @param data 数据
     */
    private ReturnBase(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    /**
     * 获取响应描编码
     * @return int
     */
    public int getCode() {
        return code;
    }
    /**
     * 获取响应描述
     * @return String
     */
    public String getMessage() {
        return message;
    }
    /**
     * 获取响应描述
     * @return String
     */
    public Object getData() {
        return data;
    }
    /**
     * 构造器
     */
    public static class ReturnBaseBuilder {
        /**
         * 响应编码
         */
        private int code;
        /**
         * 响应描述
         */
        private String message;
        /**
         * 响应数据
         */
        private Object data;
        /**
         * 构造函数
         */
        public ReturnBaseBuilder() {}
        /**
         * 设置响应编码
         * @param code 响应编码
         * @return ReturnBaseBuilder
         */
        public ReturnBaseBuilder code(int code) {
            this.code = code;
            return this;
        }
        /**
         * 设置响应描述
         * @param message 响应描述
         * @return ReturnBaseBuilder
         */
        public ReturnBaseBuilder message(String message) {
            this.message = message;
            return this;
        }
        /**
         * 设置响应数据
         * @param data 响应数据
         * @return ReturnBaseBuilder
         */
        public ReturnBaseBuilder data(Object data) {
            this.data = data;
            return this;
        }
        /**
         * 构造异常响应对象
         * @return
         */
        public ReturnBase build() {
            if (CommonConstant.Number.ZERO_INT == this.code) {
                this.code = ReturnEnum.SUCCESS.getMsgCode();
            }
            if (Objects.nonNull(ReturnEnum.getEnum(this.code))) {
                String language = UserInfoContextHolder.getContext().getLanguage();
                if (Objects.isNull(language) || CommonConstant.SIMPLE_CHINESE.equals(language)) {
                    this.message = Objects.requireNonNull(ReturnEnum.getEnum(this.code)).getMsgCn();

                } else {
                    this.message = Objects.requireNonNull(ReturnEnum.getEnum(this.code)).getMsgEn();
                }
            }
            return new ReturnBase(code, message, data);
        }
    }

}
