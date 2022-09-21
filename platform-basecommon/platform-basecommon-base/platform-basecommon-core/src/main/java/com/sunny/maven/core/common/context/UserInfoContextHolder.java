package com.sunny.maven.core.common.context;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.Assert;

import java.util.Collection;

/**
 * @author SUNNY
 * @description: 用户会话上下文存储（绑定线程）
 * @create: 2022-09-21 14:34
 */
public class UserInfoContextHolder {
    public static final UserInfoContext getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserInfoContext) authentication.getCredentials();
    }

    public static final Collection<? extends GrantedAuthority> getAuthorities() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getAuthorities();
    }

    public static final void setAuthentication(Authentication authentication) {
        Assert.notNull(authentication, "Only non-null authentication instances are permitted");
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static final UserInfoContext createEmptyContext(){
        return new UserInfoContext();
    }
}
