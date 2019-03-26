package com.liuty.maven.service.feign;

import com.liuty.maven.facade.rest.UserRestApi;

//@FeignClient(value = "microservice-rest-provider"
//        , configuration = FeignLogConfiguration.class
//        , fallbackFactory = UserFeignClientFallbackFactory.class)
public interface UserFeignClient extends UserRestApi {

}
