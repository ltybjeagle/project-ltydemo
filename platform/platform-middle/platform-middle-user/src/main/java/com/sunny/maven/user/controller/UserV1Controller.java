package com.sunny.maven.user.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUNNY
 * @description: 用户
 * @create: 2023/6/29 10:28
 */
@RestController
@RequestMapping("/user/{version}")
public class UserV1Controller {

    @GetMapping(value = "/query")
    public String query(){
        return "version1";
    }

}
