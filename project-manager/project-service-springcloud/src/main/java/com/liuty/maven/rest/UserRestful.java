package com.liuty.maven.rest;

import com.liuty.maven.config.MyProperties;
import com.liuty.maven.entity.UserEntity;
import com.liuty.maven.facade.rest.UserRestApi;
import com.liuty.maven.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserRestful implements UserRestApi {
    private static final Logger logger = LoggerFactory.getLogger(UserRestful.class);

    @Autowired
    private UserService userService;

    @Autowired
    private DiscoveryClient client;

    @Autowired
    private MyProperties myProperties;

    public UserEntity findUserById(@PathVariable("id") String id) throws Exception {
        logger.info("DiscoveryClient获取注册信息：");
        List<String> strList = client.getServices();
        strList.stream().forEach(str -> {
            List<ServiceInstance> siList = client.getInstances(str);
            siList.stream().forEach(si -> {
                logger.info("host: {}, instance: {}", si.getHost(), si.getInstanceId());
            });
        });
        UserEntity user = userService.findUserById(id);
        logger.info("findUserById，result = {}", user);
        logger.info("配置信息： {}", myProperties.toString());
        return user;
    }

    public UserEntity findUserByRequestHeader(@RequestHeader("id") String id) throws Exception {
        UserEntity user = userService.findUserById(id);
        return user;
    }

    public String modifyOneUser(@RequestBody UserEntity user) {
        return "success";
    }
}
