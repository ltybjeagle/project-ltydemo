package com.liuty.maven.service;

import com.liuty.maven.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

//    @Autowired
//    UserFeignClient userFeignClient;
    @Autowired
    RestTemplate restTemplate;

    /**
     * 根据用户ID查询用户数据
     * @param id
     * @return
     */
    public UserEntity findUserEntityById(String id) throws Exception {
        long startTime = System.currentTimeMillis();
        //UserEntity userEntity = this.userFeignClient.findUserById(id);
        UserEntity userEntity = this.restTemplate.getForObject(
                "http://springcloud-rest-provider/userRest/user/findbyid/{1}", UserEntity.class, id);
        long endTime = System.currentTimeMillis();
        System.out.println("springcloud-rest-provider服务调用时间：" + (endTime - startTime) + "ms");
        return userEntity;
    }
}
