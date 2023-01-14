package com.sunny.maven.user.controller;

import com.sunny.maven.core.common.annotation.NotControllerResponseAdvice;
import com.sunny.maven.user.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUNNY
 * @description: 认证鉴权
 * @create: 2022-09-23 10:53
 */
@Slf4j
@RestController
public class AuthController {

    private AuthService authService;

    /**
     * 认证函数
     * @param token
     * @return
     */
    @NotControllerResponseAdvice
    @GetMapping(value = "/auth/token/userinfo")
    public String verifyToken(@RequestParam("token") String token) {
        log.info("接收认证token={}", token);
        String userInfo = authService.verifyToken(token);
        log.info("认证结果userInfo={}", userInfo);
        return userInfo;
    }

    /**
     * 构造函数
     * @param authService
     */
    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
}
