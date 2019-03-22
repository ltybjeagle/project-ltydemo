package com.liuty.maven.facade.dubbo;

import com.liuty.maven.entity.UserEntity;

public interface UserFacade {

    UserEntity findUserById(String id) throws Exception;
}
