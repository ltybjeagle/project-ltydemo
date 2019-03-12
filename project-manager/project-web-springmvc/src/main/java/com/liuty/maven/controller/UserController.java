package com.liuty.maven.controller;

import com.liuty.maven.entity.UserEntity;
import com.liuty.maven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * 用户管理控制
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    /**
     * 用户列表
     * @return
     */
    @RequestMapping(value = "/listuser", method = GET)
    public String listUser() {
        System.out.println("listuser");
        UserEntity userEntity = userService.findUserById("844463533D391D932801CA8806F60516");
        System.out.println(userEntity);
        return "listuser";
    }

    /**
     * 获取用户对象
     * @param id
     * @return
     */
    @RequestMapping(value = "/getuser/{userid}", method = GET)
    public String getUser(@PathVariable("userid") String id) {
        System.out.println("oneuser, id = " + id);
        return "oneuser";
    }

    /**
     * 注册用户
     * @param multipartFile
     * @return
     */
    @RequestMapping(value = "/register", method = POST)
    public String registerUser(@RequestPart("profilePicture")MultipartFile multipartFile) {
        return "listuser";
    }
}
