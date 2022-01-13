package com.sunny.maven.usercenter.feign.client.fallback;

import com.sunny.maven.usercenter.api.UserInfoApi;
import feign.hystrix.FallbackFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: 接口容错
 * @create: 2022-01-12 13:28
 */
@Component
public class UserInfoFeignClientFallbackFactory implements FallbackFactory<UserInfoApi> {
    private static final Logger logger = LoggerFactory.getLogger(UserInfoFeignClientFallbackFactory.class);
    /**
     * 用户中心客户端降级容错实现
     * @param throwable 异常对象
     * @return UserRemoteClient
     */
    @Override
    public UserInfoApi create(Throwable throwable) {
        logger.error("UserInfoFeignClient回退：", throwable);
        return () -> "fail";
    }
}
