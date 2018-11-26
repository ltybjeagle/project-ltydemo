package com.liuty.maven.service.feign;

import com.liuty.maven.config.FeignLogConfiguration;
import com.liuty.maven.serviceapi.UserRestApi;
import org.springframework.cloud.netflix.feign.FeignClient;

@FeignClient(value = "microservice-provider-user"
        , configuration = FeignLogConfiguration.class
        , fallbackFactory = UserFeignClientFallbackFactory.class)
public interface UserFeignClient extends UserRestApi {

}
