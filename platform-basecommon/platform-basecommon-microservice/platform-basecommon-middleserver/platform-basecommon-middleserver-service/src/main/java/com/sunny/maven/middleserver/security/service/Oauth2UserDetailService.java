package com.sunny.maven.middleserver.security.service;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * @author SUNNY
 * @description: 用户服务
 * @create: 2022-04-14 14:43
 */
@Service
public class Oauth2UserDetailService implements UserDetailsService {
    /**
     * 加密方式
     */
    private PasswordEncoder passwordEncoder;
    /**
     * 获取登录用户
     * @param userCode 登录用户编码
     * @return UserDetails
     * @throws UsernameNotFoundException 异常
     */
    @Override
    public UserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {
        return new User("admin", passwordEncoder.encode("123456"),
                AuthorityUtils.commaSeparatedStringToAuthorityList("admin_role"));
    }

    /**
     * 构造函数
     * @param passwordEncoder 加密方式
     */
    public Oauth2UserDetailService(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }
}
