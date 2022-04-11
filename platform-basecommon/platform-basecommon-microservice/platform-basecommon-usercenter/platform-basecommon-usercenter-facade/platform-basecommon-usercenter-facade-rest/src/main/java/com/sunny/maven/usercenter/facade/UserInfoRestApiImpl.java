package com.sunny.maven.usercenter.facade;

import com.sunny.maven.usercenter.api.UserInfoApi;
import com.sunny.maven.usercenter.common.constant.UserCenterConstant;
import com.sunny.maven.usercenter.common.context.UserCenterContext;
import com.sunny.maven.usercenter.service.UserInfoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUNNY
 * @description: 用户信息接口REST实现类
 * @create: 2022-01-11 23:56
 */
@RestController
@RequestMapping("/userinfo")
public class UserInfoRestApiImpl implements UserInfoApi {
    /**
     * 用户中心测试API
     * @return String
     */
    @Override
    @GetMapping("/user/test")
    public String getTestUserName() {
        UserInfoService userInfoService = UserCenterContext.getBean(UserCenterConstant.USER_INFO_SERVICE);
        return userInfoService.getTestUserName();
    }
}
