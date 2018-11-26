package com.liuty.maven.serviceapi;

import com.liuty.maven.entity.UserEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("userRest")
public interface UserRestApi {

    @RequestMapping(value = "/user/findbyid/{id}", method = RequestMethod.GET)
    UserEntity findUserById(@PathVariable("id") String id) throws Exception;

    @RequestMapping(value = "/user/findbyHeader/reqh", method = RequestMethod.GET)
    UserEntity findUserByRequestHeader(@RequestHeader("id") String id) throws Exception;

    @RequestMapping(value = "/user/modifyoneuser", method = RequestMethod.POST)
    String modifyOneUser(@RequestBody UserEntity user);
}
