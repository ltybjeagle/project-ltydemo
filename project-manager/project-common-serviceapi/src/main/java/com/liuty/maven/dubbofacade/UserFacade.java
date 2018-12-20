package com.liuty.maven.dubbofacade;

import com.liuty.maven.entity.UserEntity;

public interface UserFacade {

    UserEntity findUserById(String id) throws Exception;
}
