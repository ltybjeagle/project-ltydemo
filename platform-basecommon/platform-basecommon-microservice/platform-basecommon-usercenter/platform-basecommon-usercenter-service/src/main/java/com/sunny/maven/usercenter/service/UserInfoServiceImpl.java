package com.sunny.maven.usercenter.service;

import com.sunny.maven.usercenter.common.constant.UserCenterConstant;
import org.springframework.stereotype.Service;

/**
 * @author SUNNY
 * @description: 用户信息服务类
 * @create: 2022-02-22 13:57
 */
@Service(value = UserCenterConstant.USER_INFO_SERVICE)
public class UserInfoServiceImpl implements UserInfoService {

    /**
     * 用户中心测试函数
     * @return String
     */
    @Override
    public String getTestUserName() {
        return "hello";
    }
}
