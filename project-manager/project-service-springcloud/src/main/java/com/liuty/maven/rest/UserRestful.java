package com.liuty.maven.rest;

import com.liuty.maven.entity.UserEntity;
import com.liuty.maven.service.UserService;
import com.liuty.maven.serviceapi.UserRestApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestful implements UserRestApi {

    @Autowired
    private UserService userService;

    public UserEntity findUserById(@PathVariable("id") String id) throws Exception {
        UserEntity user = userService.findUserById(id);
        return user;
    }

    public UserEntity findUserByRequestHeader(@RequestHeader("id") String id) throws Exception {
        UserEntity user = userService.findUserById(id);
        return user;
    }

    public String modifyOneUser(@RequestBody UserEntity user) {
        return "success";
    }
}
