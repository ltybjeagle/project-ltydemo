package com.liuty.maven.dao;

import com.liuty.maven.entity.UserEntity;

public interface UserDao {

    UserEntity findUserById(String id);
}
