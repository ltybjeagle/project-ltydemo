package com.sunny.maven.microservice.order.fegin;

import com.sunny.maven.microservice.bean.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author SUNNY
 * @description: 调用用户微服务的接口
 * @create: 2023-03-24 16:03
 */
@FeignClient("microservice-user")
public interface UserService {
    @GetMapping(value = "/user/get/{uid}")
    User getUser(@PathVariable("uid") Long uid);
}
