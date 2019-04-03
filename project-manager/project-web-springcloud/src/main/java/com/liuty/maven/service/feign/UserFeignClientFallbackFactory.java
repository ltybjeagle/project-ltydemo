package com.liuty.maven.service.feign;

import com.liuty.maven.entity.UserEntity;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserFeignClientFallbackFactory implements FallbackFactory<UserFeignClient> {
    private static final Logger logger = LoggerFactory.getLogger(UserFeignClientFallbackFactory.class);

    @Override
    public UserFeignClient create(Throwable throwable) {
        logger.info("服务调用异常，异常信息：{} ", throwable.getMessage());
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
            defaultUser.setGuid(-1);
            defaultUser.setName("默认用户");
            defaultUser.setCode("000000");
        }
    }
}
