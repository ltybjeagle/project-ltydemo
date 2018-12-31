package com.liuty.maven.service;

import com.codahale.metrics.annotation.Counted;
import com.codahale.metrics.annotation.Timed;
import com.liuty.maven.cache.UserGuavaCache;
import com.liuty.maven.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserGuavaCache userGuavaCache;

    /**
     * 根据ID查询用户
     * @param id
     * @return
     * @throws Exception
     */
    @Timed
    @Counted
    public UserEntity findUserById(String id) throws Exception {
        UserEntity userEntity = userGuavaCache.getCacheValue(id);
        return userEntity;
    }
}
