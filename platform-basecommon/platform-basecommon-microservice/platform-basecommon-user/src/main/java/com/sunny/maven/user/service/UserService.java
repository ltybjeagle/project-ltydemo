package com.sunny.maven.user.service;

import com.sunny.maven.core.common.resp.R;
import org.springframework.security.core.AuthenticationException;

/**
 * @author SUNNY
 * @description: 用户业务接口
 * @create: 2022-06-22 23:49
 */
public interface UserService {
    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return String
     * @throws AuthenticationException
     */
    String login(String username, String password) throws AuthenticationException;
}
