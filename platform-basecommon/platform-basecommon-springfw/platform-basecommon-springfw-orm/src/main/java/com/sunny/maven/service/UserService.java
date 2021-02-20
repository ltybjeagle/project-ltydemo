package com.sunny.maven.service;

import com.sunny.maven.dto.User;
import org.springframework.stereotype.Service;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/7/29 23:44
 * @description：用户业务逻辑对象
 * @modified By：
 * @version: 1.0.0
 */
@Service
public class UserService implements IUserService {
    @Override
    public String addUser(User user) {
        return "success";
    }
}
