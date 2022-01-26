package com.sunny.maven.core.exception;

/**
 * @author SUNNY
 * @description: 业务异常
 * @create: 2022-01-13 23:11
 */
public class AppException extends RuntimeException {
    private static final long serialVersionUID = -8761835844276751464L;
    /**
     * 异常码
     */
    protected int code = 0;
    /**
     * 构造函数
     */
    private AppException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    /**
     * 异常对象构造器
     */
    public static class AppExceptionBuilder {
        /**
         * 异常编码
         */
        private int code;
        /**
         * 异常描述
         */
        private String message;
        /**
         * 构造函数
         */
        public AppExceptionBuilder() {}
        /**
         * 设置编码
         * @param code 编码
         * @return AppExceptionBuilder
         */
        public AppExceptionBuilder code(int code) {
            this.code = code;
            return this;
        }
        /**
         * 设置异常描述
         * @param message 异常描述
         * @return AppExceptionBuilder
         */
        public AppExceptionBuilder message(String message) {
            this.message = message;
            return this;
        }
        /**
         * 构造异常队形
         * @return AppException
         */
        public AppException build() {
            return new AppException(code, message);
        }
    }
}
