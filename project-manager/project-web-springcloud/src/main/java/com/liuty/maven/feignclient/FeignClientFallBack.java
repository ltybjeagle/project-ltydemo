package com.liuty.maven.feignclient;

import com.liuty.maven.entity.DemoUser;
import org.springframework.stereotype.Component;

@Component
public class FeignClientFallBack implements DemoUserFeignClient {

    @Override
    public DemoUser findById(String id) {
        DemoUser demoUser = new DemoUser();
        demoUser.setGuid("-1");
        demoUser.setName("默认用户");
        demoUser.setCode("000000");
        return demoUser;
    }

}
