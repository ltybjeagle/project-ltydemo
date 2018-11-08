package com.liuty.maven.feignclient;

import com.liuty.maven.config.FeignConfiguration;
import com.liuty.maven.config.FeignLogConfiguration;
import com.liuty.maven.entity.DemoUser;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "microservice-provider-user", configuration =
        {FeignConfiguration.class, FeignLogConfiguration.class})
public interface DemoUserFeignClient {

    /*
    @RequestLine("GET /{id}")
    public DemoUser findById(@Param("id") String id);*/

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DemoUser findById(@PathVariable(value = "id") String id);
}
