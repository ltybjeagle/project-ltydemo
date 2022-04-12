package com.sunny.maven.usercenter.security.service;

import com.google.common.collect.Lists;
import com.sunny.maven.usercenter.security.mapper.Oauth2UserMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Objects;

/**
 * @author SUNNY
 * @description: 用户服务
 * @create: 2022-02-08 15:17
 */
@Service
public class Oauth2UserDetailService implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(Oauth2UserDetailService.class);
    /**
     * 用户信息
     */
    private Oauth2UserMapper oauth2UserMapper;

    /**
     * 获取登录用户
     * @param userCode 登录用户编码
     * @return UserDetails
     * @throws UsernameNotFoundException 异常
     */
    @Override
    public UserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {
        logger.info("login userCode: {}", userCode);
        Collection<GrantedAuthority> grantedAuthorities = Lists.newArrayList();
//        Oauth2UserDetails userDetails = oauth2UserMapper.findByLoginCode(userCode);
//        if (Objects.isNull(userDetails)) {
//            throw new UsernameNotFoundException("无此用户");
//        }
//        grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
//        userDetails.setAuthorities(grantedAuthorities);
//        return userDetails;
        return null;
    }

    /**
     * 构造函数
     * @param oauth2UserMapper 用户信息
     */
    @Autowired
    public Oauth2UserDetailService(Oauth2UserMapper oauth2UserMapper) {
        this.oauth2UserMapper = oauth2UserMapper;
    }
}
