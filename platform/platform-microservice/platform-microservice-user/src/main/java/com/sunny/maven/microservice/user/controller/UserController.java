package com.sunny.maven.microservice.user.controller;

import com.alibaba.fastjson.JSONObject;
import com.sunny.maven.microservice.bean.User;
import com.sunny.maven.microservice.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUNNY
 * @description: 用户接口
 * @create: 2023-03-23 11:07
 */
@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(value = "/get/{uid}")
    public User getUser(@PathVariable("uid") Long uid) {
        User user = userService.getUserById(uid);
        log.info("获取到的用户信息为：{}", JSONObject.toJSONString(user));
        return user;
    }
}
