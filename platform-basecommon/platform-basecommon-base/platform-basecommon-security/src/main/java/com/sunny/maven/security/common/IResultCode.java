package com.sunny.maven.security.common;

/**
 * @author SUNNY
 * @description: 统一返回结果接口
 * @create: 2022-04-12 17:25
 */
public interface IResultCode {
    /**
     * 返回码
     * @return int
     */
    int getCode();
    /**
     * 返回消息
     * @return String
     */
    String getMsg();
}
