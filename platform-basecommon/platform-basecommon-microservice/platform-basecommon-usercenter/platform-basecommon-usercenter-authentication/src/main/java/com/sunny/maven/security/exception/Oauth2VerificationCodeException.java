package com.sunny.maven.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author SUNNY
 * @description: 验证码异常
 * @create: 2022-02-08 18:18
 */
public class Oauth2VerificationCodeException extends AuthenticationException {
    /**
     * 构造函数
     */
    public Oauth2VerificationCodeException() {
        super("图形验证码校验失败");
    }
}
