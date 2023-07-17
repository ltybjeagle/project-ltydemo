package com.sunny.maven.microservice.user.service;

import com.sunny.maven.microservice.bean.User;

/**
 * @author SUNNY
 * @description: 用户业务接口
 * @create: 2023-03-23 11:02
 */
public interface UserService {
    /**
     * 根据id获取用户信息
     */
    User getUserById(Long userId);

    void asyncMethod();
}
