package com.sunny.maven.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUNNY
 * @description: 管理员
 * @create: 2022-02-03 18:55
 */
@RestController
@RequestMapping("/admin/api")
public class AdminController {
    @GetMapping("hello")
    public String hello() {
        return "hello, admin";
    }
}
