package com.sunny.maven.core.common.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * @author SUNNY
 * @description: 用户会话对象
 * @create: 2022-09-22 11:22
 */
@Data
public class UserDto {
    private String username;
    private Collection<? extends GrantedAuthority> authorities;

    public static UserDto createUserDto() {
        return new UserDto();
    }
}
