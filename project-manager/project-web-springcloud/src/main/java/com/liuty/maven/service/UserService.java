package com.liuty.maven.service;

import com.liuty.maven.entity.UserEntity;
import com.liuty.maven.service.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserFeignClient userFeignClient;

    /**
     * 根据用户ID查询用户数据
     * @param id
     * @return
     */
    public UserEntity findUserEntityById(String id) throws Exception {
        long startTime = System.currentTimeMillis();
        UserEntity userEntity = this.userFeignClient.findUserById(id);
        long endTime = System.currentTimeMillis();
        System.out.println("microservice-provider-user服务调用时间：" + (endTime - startTime) + "ms");
        return userEntity;
    }
}
