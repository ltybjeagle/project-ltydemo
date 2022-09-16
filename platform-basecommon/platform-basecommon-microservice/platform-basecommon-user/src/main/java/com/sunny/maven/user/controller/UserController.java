package com.sunny.maven.user.controller;

import com.sunny.maven.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUNNY
 * @description: 用户接口
 * @create: 2022-06-22 23:54
 */
@Slf4j
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
}
