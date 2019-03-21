package com.liuty.maven.rest;

import com.liuty.maven.entity.UserEntity;
import com.liuty.maven.service.UserService;
import com.liuty.maven.serviceapi.UserRestApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestful implements UserRestApi {
    private static final Logger logger = LoggerFactory.getLogger(UserRestful.class);

    @Autowired
    private UserService userService;

    public UserEntity findUserById(@PathVariable("id") String id) throws Exception {
        UserEntity user = userService.findUserById(id);
        logger.info("findUserById，result = {}", user);
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
