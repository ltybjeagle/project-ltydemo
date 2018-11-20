package com.liuty.maven.feignclient;

import com.liuty.maven.entity.UserEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Feign默认整合了Hystrix
 */
//@FeignClient(name = "microservice-provider-user",
//        configuration = {FeignConfiguration.class, FeignLogConfiguration.class},
//        fallbackFactory = FeignClientFallbackFactory.class)
public interface DemoUserFeignClient {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    UserEntity findById(@PathVariable(value = "id") String id);
}


