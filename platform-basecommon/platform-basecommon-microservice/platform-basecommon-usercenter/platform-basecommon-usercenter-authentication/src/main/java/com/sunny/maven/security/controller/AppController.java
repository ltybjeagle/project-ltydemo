package com.sunny.maven.security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUNNY
 * @description: 通用
 * @create: 2022-02-03 18:57
 */
@RestController
@RequestMapping("/app/api")
public class AppController {
    @GetMapping("hello")
    public String hello() {
        return "hello, app";
    }
}
