package com.sunny.maven.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Sunny
 * @date: 2019/12/28
 */
@RestController
@RequestMapping("/app/api")
public class AppController {
    @RequestMapping("hello")
    public String hello() {
        return "hello, app";
    }
}
