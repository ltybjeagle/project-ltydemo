package com.liuty.maven.feignclient;

import com.liuty.maven.config.FeignConfiguration;
import com.liuty.maven.entity.DemoUser;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(name = "microservice-provider-user", configuration = FeignConfiguration.class)
public interface DemoUserFeignClient {

    @RequestLine("GET /{id}")
    public DemoUser findById(@Param("id") String id);
}
