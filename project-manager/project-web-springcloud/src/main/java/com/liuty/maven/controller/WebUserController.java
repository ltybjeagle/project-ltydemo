package com.liuty.maven.controller;

import com.liuty.maven.entity.UserEntity;
import com.liuty.maven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebUserController {

    @Autowired
    private UserService userService;

    @GetMapping("/feign-user/{id}")
    public UserEntity findById(@PathVariable String id) {
        UserEntity userEntity = null;
        try {
            userEntity = userService.findUserEntityById(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return userEntity;
    }
}
