package com.sunny.maven.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUNNY
 * @description: 用户
 * @create: 2022-02-03 18:58
 */
@RestController
@RequestMapping("/user/api")
public class UserController {
    @GetMapping("hello")
    public String hello() {
        return "hello, user";
    }
}
