package com.liuty.maven.service.feign;

import com.liuty.maven.entity.UserEntity;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UserFeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {

    @Override
    public UserFeignClient create(Throwable throwable) {
        System.out.println("发生异常：" + throwable.getMessage());
        return new UserFeignClient() {

            @Override
            public UserEntity findUserById(String id) throws Exception {
                return UserFeignClientFallbackFactory.getDefaultUserEntity();
            }

            @Override
            public UserEntity findUserByRequestHeader(String id) throws Exception {
                return UserFeignClientFallbackFactory.getDefaultUserEntity();
            }

            @Override
            public String modifyOneUser(UserEntity user) {
                return "error";
            }
        };
    }

    private static UserEntity getDefaultUserEntity() {
        return UserFeignClientFallbackFactory.DefaultUserEntity.defaultUser;
    }

    private static class DefaultUserEntity {
        private static UserEntity defaultUser = new UserEntity();
        static {
            defaultUser.setGuid("-1");
            defaultUser.setName("默认用户");
            defaultUser.setCode("000000");
        }
    }
}
