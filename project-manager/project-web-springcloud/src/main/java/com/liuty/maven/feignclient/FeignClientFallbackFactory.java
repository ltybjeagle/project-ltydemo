package com.liuty.maven.feignclient;

import com.liuty.maven.entity.DemoUser;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class FeignClientFallbackFactory implements FallbackFactory<DemoUserFeignClient> {
    @Override
    public DemoUserFeignClient create(Throwable throwable) {
        return new DemoUserFeignClient() {
            @Override
            public DemoUser findById(String id) {
                System.out.println("fallback : " + throwable);
                DemoUser demoUser = new DemoUser();
                demoUser.setGuid("-1");
                demoUser.setName("默认用户");
                demoUser.setCode("000000");
                return demoUser;
            }
        };
    }
}
