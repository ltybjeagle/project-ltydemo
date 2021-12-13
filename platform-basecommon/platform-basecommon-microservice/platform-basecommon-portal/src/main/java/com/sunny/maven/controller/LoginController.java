package com.sunny.maven.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author SUNNY
 * @description: 登录Rest接口
 * @create: 2021-11-26 15:17
 */
@RestController
public class LoginController {

    private RestTemplate restTemplate;
    private static final String USER_CENTER = "usercenter-service";

    @GetMapping("/portal/login")
    public String callHello() {
        String url = String.format("http://%s/user/hello", USER_CENTER);
        return restTemplate.getForObject(url, String.class);
    }

    /**
     * 构造函数
     * @param restTemplate REST模板
     */
    @Autowired
    public LoginController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
