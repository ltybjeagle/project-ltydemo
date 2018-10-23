package com.liuty.maven.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * 用户管理控制
 */
@Controller
@RequestMapping("/user")
public class UserController {

    /**
     * 用户列表
     * @return
     */
    @RequestMapping(value = "/listuser", method = GET)
    public String listUser() {
        System.out.println("listuser");
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
