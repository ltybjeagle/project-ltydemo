package com.sunny.maven.microservice.authentication.jwt.password.codec;

import org.springframework.core.env.Environment;

/**
 * @author SUNNY
 * @description: 密码处理工具
 * @create: 2023-04-03 22:16
 */
public interface PasswordCodec {

    /**
     * 密码编码
     * @param password 密码
     * @return  String
     * @throws Exception 异常
     */
    String passwordEncode(String password) throws Exception;

    /**
     * 初始化
     * @param env
     */
    default void init(Environment env) {}
}
