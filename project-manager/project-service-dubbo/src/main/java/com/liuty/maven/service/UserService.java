package com.liuty.maven.service;

import com.liuty.maven.dao.UserDao;
import com.liuty.maven.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("userService")
public class UserService {

    @Autowired
    private UserDao userDao;

    public UserEntity findUserById(String id) {
        return userDao.findUserById(id);
    }
}
