package com.sunny.maven.user.controller;

import com.sunny.maven.core.annotation.common.ApiVersion;
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
@ApiVersion(2)
public class UserV2Controller {

    @GetMapping(value = "/query")
    public String query(){
        return "version2";
    }

}
