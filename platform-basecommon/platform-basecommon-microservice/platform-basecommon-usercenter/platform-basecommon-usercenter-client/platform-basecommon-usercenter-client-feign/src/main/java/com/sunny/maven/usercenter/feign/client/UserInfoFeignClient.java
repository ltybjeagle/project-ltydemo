package com.sunny.maven.usercenter.feign.client;

import com.sunny.maven.usercenter.api.UserInfoApi;
import com.sunny.maven.usercenter.common.constant.UserCenterConstant;
import com.sunny.maven.usercenter.feign.client.fallback.UserInfoFeignClientFallbackFactory;
import com.sunny.maven.usercenter.feign.config.UserCenterFeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author SUNNY
 * @description: 用户中心Feign客户端
 * @create: 2022-01-12 11:46
 */
@Component
@FeignClient(value = UserCenterConstant.APPLICATION_NAME, fallbackFactory = UserInfoFeignClientFallbackFactory.class,
        configuration = UserCenterFeignConfiguration. class)
public interface UserInfoFeignClient extends UserInfoApi {

    /**
     * 用户中心测试API
     * @return String
     */
    @Override
    @GetMapping("/userinfo/user/test")
    String getTestUserName();
}
