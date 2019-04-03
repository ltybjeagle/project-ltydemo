package com.liuty.maven.service.feign;

import com.liuty.maven.config.FeignLogConfiguration;
import com.liuty.maven.facade.rest.UserRestApi;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient(value = "springcloud-rest-provider",
        configuration = FeignLogConfiguration.class,
        fallbackFactory = UserFeignClientFallbackFactory.class)
public interface UserFeignClient extends UserRestApi {

}
