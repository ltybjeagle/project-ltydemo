package com.liuty.maven.feignclient;

import com.liuty.maven.config.FeignConfiguration;
import com.liuty.maven.config.FeignLogConfiguration;
import com.liuty.maven.entity.DemoUser;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Feign默认整合了Hystrix
 */
@FeignClient(name = "microservice-provider-user",
        configuration = {FeignConfiguration.class, FeignLogConfiguration.class},
        fallback = FeignClientFallBack.class)
public interface DemoUserFeignClient {

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    DemoUser findById(@PathVariable(value = "id") String id);
}


