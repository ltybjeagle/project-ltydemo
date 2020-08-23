package com.sunny.maven.controller;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description:
 * @author: Sunny
 * @date: 2019/12/28
 */
//@RestController
//@RequestMapping("/admin/api")
public class AdminController {
    @RequestMapping("hello")
    public String hello() {
        return "hello, admin";
    }
}
