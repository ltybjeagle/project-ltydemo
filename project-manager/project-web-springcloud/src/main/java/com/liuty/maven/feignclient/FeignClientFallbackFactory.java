//package com.liuty.maven.feignclient;
//
//import com.liuty.maven.entity.UserEntity;
////import feign.hystrix.FallbackFactory;
//import org.springframework.stereotype.Component;
//
//@Component
//public class FeignClientFallbackFactory implements FallbackFactory<DemoUserFeignClient> {
//    @Override
//    public DemoUserFeignClient create(Throwable throwable) {
//        return new DemoUserFeignClient() {
//            @Override
//            public UserEntity findById(String id) {
//                System.out.println("fallback : " + throwable);
//                UserEntity demoUser = new UserEntity();
//                demoUser.setGuid("-1");
//                demoUser.setName("默认用户");
//                demoUser.setCode("000000");
//                return demoUser;
//            }
//        };
//    }
//}
