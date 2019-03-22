package com.liuty.maven.facade;

import com.liuty.maven.facade.dubbo.UserFacade;
import com.liuty.maven.entity.UserEntity;
import com.liuty.maven.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;

public class UserFacadeImpl implements UserFacade {

    @Autowired
    private UserService userService;

    @Override
    public UserEntity findUserById(String id) throws Exception {
        return userService.findUserById(id);
    }
}
