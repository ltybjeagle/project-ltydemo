package com.sunny.maven.user.service.impl;

import com.sunny.maven.user.mapper.UserMapper;
import com.sunny.maven.user.security.JwtTokenUtil;
import com.sunny.maven.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

/**
 * @author SUNNY
 * @description: 用户业务实现类
 * @create: 2022-06-22 23:50
 */
@Slf4j
//@Service
public class UserServiceImpl implements UserService {

    private UserMapper userMapper;
    private AuthenticationManager authenticationManager;
    private UserDetailsService jwtUserDetailsServiceImpl;

    /**
     * 登录
     * @param username 用户名
     * @param password 密码
     * @return String
     * @throws AuthenticationException
     */
    @Override
    public String login(String username, String password) throws AuthenticationException {
        UserDetails userDetails = jwtUserDetailsServiceImpl.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //return jwtTokenUtil.generateToken(userDetails);
        return null;
    }

    @Autowired
    public UserServiceImpl(UserMapper userMapper, AuthenticationManager authenticationManager,
                           UserDetailsService jwtUserDetailsServiceImpl) {
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtUserDetailsServiceImpl = jwtUserDetailsServiceImpl;
    }
}
