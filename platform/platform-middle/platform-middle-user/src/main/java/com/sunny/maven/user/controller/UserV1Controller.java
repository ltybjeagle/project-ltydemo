package com.sunny.maven.user.controller;

import com.sunny.maven.user.service.UserV1Service;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUNNY
 * @description: 用户
 * @create: 2023/6/29 10:28
 */
@RestController
@Api(tags = "用户版本接口")
@RequestMapping("/user/{version}")
public class UserV1Controller {

    private UserV1Service userV1Service;
    @ApiOperation(value = "测试接口", notes = "测试接口描述")
    @GetMapping(value = "/query")
    public String query(){
        return userV1Service.query();
    }

    @Autowired
    public UserV1Controller(UserV1Service userV1Service) {
        this.userV1Service = userV1Service;
    }

}
