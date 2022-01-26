package com.sunny.maven.core.common.constant;

/**
 * @author SUNNY
 * @description: 公共常量定义
 * @create: 2021-11-17 13:58
 */
public class CommonConstant {
    public static final long FILE_MAX_SIZE = 5 * 1024 * 1024;
    public static final long SESSION_MAX_TIME = 5 * 60 * 1000;
    /**
     * 用户请求token
     */
    public static final String TOKEN_ID = "tokenid";
    /**
     * 响应状态标识
     */
    public static final String SUCCESS = "success";
    /**
     * AES加密
     */
    public static final String AES_KEY = "abcdefghijklmn12";
    /**
     * AES加密
     */
    public static final String AES_IV = "abcdefghijklmn12";
    /**
     * 简体中文
     */
    public static final String SIMPLE_CHINESE = "zh-CN,zh;q=0.9,en;q=0.8,en-GB;q=0.7,en-US;q=0.6";
    /**
     * 登陆用户上下文
     */
    public static final String USER_CONTEXT = "USER_CONTEXT";
    /**
     * 常用整形数
     */
    public interface Number {
        int THOUSAND_INT = 1000;
        int HUNDRED_INT = 100;
        int ZERO_INT= 0;
        int ONE_INT = 1;
        int TWO_INT = 2;
        int THREE_INT = 3;
        int FOUR_INT = 4;
        int FIVE_INT = 5;
        int SIX_INT = 6;
        int SEVEN_INT = 7;
        int EIGHT_INT = 8;
        int NINE_INT = 9;
        int TEN_INT = 10;
    }
}
