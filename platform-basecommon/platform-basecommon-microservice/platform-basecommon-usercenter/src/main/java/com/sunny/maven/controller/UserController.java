package com.sunny.maven.controller;

import com.sunny.maven.register.ServiceInsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUNNY
 * @description: 用户REST接口
 * @create: 2021-11-26 12:00
 */
@RestController
public class UserController {

    private ServiceInsClient serviceInsClient;

    @GetMapping("/user/hello")
    public String hello() {
        return "hello";
    }

    @GetMapping("/user/info")
    public Object serviceUrl() {
        return serviceInsClient.getServiceInsByName("usercenter-service");
    }

    /**
     * 构造函数
     * @param serviceInsClient serviceInsClient
     */
    @Autowired
    public UserController(ServiceInsClient serviceInsClient) {
        this.serviceInsClient = serviceInsClient;
    }
}
