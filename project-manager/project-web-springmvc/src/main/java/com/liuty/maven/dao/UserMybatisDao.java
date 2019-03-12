package com.liuty.maven.dao;

import com.liuty.maven.entity.UserEntity;
import org.mybatis.spring.annotation.MapperScan;

@MapperScan
public interface UserMybatisDao {

    UserEntity findUserById(String id);
}
