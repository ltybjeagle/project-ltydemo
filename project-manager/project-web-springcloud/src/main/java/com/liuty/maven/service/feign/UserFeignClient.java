package com.liuty.maven.service.feign;

import com.liuty.maven.entity.UserEntity;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "microservice-provider-user")
public interface UserFeignClient {

    @RequestMapping(value = "/user/findbyid/{id}", method = RequestMethod.GET)
    UserEntity findUserEntityById(@PathVariable(value = "id") String id);

    @RequestMapping(value = "/user/findbyid/reqh", method = RequestMethod.GET)
    UserEntity findUserByRequestHeader(@RequestHeader(value = "id") String id);

    @RequestMapping(value = "/user/modifyoneuser", method = RequestMethod.POST)
    String modifyOneUser(@RequestBody UserEntity user);
}
