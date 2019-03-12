package com.liuty.maven.service;

import com.liuty.maven.dao.UserMybatisDao;
import com.liuty.maven.entity.UserEntity;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class UserService {

    @Resource
    private UserMybatisDao userMybatisDao;

    public UserEntity findUserById(String id) {
        return userMybatisDao.findUserById(id);
    }
}
