package com.liuty.maven.service;

import com.liuty.maven.entity.UserEntity;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "findByIdFallBack" , ignoreExceptions = {}
            , commandKey = "userKey", groupKey = "userGroupKey", threadPoolKey = "userThreadKey")
    public UserEntity findById(String id) {
        long startTime = System.currentTimeMillis();
        UserEntity userEntity = this.restTemplate.getForEntity("http://microservice-provider-user/" + id
                , UserEntity.class).getBody();
        long endTime = System.currentTimeMillis();
        System.out.println("microservice-provider-user服务调用时间：" + (endTime - startTime) + "ms");
        return userEntity;
    }

    /**
     * Hystrix断路器开启，提供默认方法
     * @param id
     * @param e
     * @return
     */
    public UserEntity findByIdFallBack(String id, Throwable e) {
        System.out.println("findById(String id) 异常：" + e.getMessage());
        UserEntity defaultUser = new UserEntity();
        defaultUser.setGuid("-1");
        defaultUser.setName("默认用户");
        defaultUser.setCode("000000");
        return defaultUser;
    }
}
