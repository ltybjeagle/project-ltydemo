package com.sunny.maven.exception;

/**
 * @author SUNNY
 * @description: 自定义异常
 * @create: 2021-08-21 16:44
 */
public class AppException extends Exception {
    /**
     * 异常编码
     */
    private String code;
    /**
     * 处理建议
     */
    private String advise;
    /**
     * 构造器
     */
    private AppException(String code, String message, String advise) {
        super(message);
        this.code = code;
        this.advise = advise;
    }
    /**
     * 获取异常编码
     * @return String
     */
    public String getCode() {
        return code;
    }
    /**
     * 获取处理建议
     * @return String
     */
    public String getAdvise() {
        return advise;
    }
    /**
     * 异常对象构造器
     */
    public static class AppExceptionBuilder {
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
         * 无参构造器
         */
        public AppExceptionBuilder() {

        }
        /**
         * 设置编码
         * @param code 编码
         * @return AppExceptionBuilder
         */
        public AppExceptionBuilder code(String code) {
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
         * 处理意见
         * @param advise 处理意见
         * @return AppExceptionBuilder
         */
        public AppExceptionBuilder advise(String advise) {
            this.advise = advise;
            return this;
        }
        /**
         * 构造异常队形
         * @return AppException
         */
        public AppException build() {
            return new AppException(code, message, advise);
        }
    }
}
