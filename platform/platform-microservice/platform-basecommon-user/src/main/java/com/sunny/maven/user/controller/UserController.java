package com.sunny.maven.user.controller;

import com.sunny.maven.core.common.context.UserInfoContext;
import com.sunny.maven.core.common.context.UserInfoContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUNNY
 * @description: 用户接口
 * @create: 2022-06-22 23:54
 */
@Slf4j
@RestController
public class UserController {

    @GetMapping(value = "/api/getcontext")
    public String asyncApi() {
        log.info("/api/getcontext开始...");
        UserInfoContext userInfoContext = UserInfoContextHolder.getAuthentication();
        log.info("userInfoContext：{}", userInfoContext);
        log.info("Authorities：{}", UserInfoContextHolder.getAuthorities());
        log.info("/api/getcontext结束...");
        return "asyncApi";
    }
}
