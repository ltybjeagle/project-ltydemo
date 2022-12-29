package com.sunny.maven.middle.user.controller;

import com.sunny.maven.middle.user.entity.User;
import com.sunny.maven.middle.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

/**
 * @author SUNNY
 * @description: 用户管理
 * @create: 2022-09-29 15:00
 */
@RestController
@RequestMapping("/controller/user")
public class UserController {
    private UserService userService;

    /**
     * 返回所有用户数据
     * @return
     */
    @GetMapping("/all")
    public Flux<User> getUsers() {
        return userService.getUsers();
    }

    /**
     * 使用流的方式获取用户数据（以SSE形式多次放回数据）
     * @return
     */
    @GetMapping(value = "/stream/all", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<User> streamGetAll() {
        return userService.getUsers();
    }

    /**
     * 根据ID查找
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    public Mono<User> getUserById(@PathVariable("id") final String id) {
        return userService.getUserById(id);
    }

    /**
     * 新增用户
     * @param user
     * @return
     */
    @PostMapping("/save")
    public Mono<Void> createUser(@RequestBody final Mono<User> user) {
        return userService.createOrUpdateUser(user);
    }

    /**
     * 根据ID删除
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public Mono<User> deleteUser(@PathVariable("id") final String id) {
        return userService.deleteUser(id);
    }

    /**
     * 构造
     * @param userService
     */
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }
}
