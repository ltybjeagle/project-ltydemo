package com.sunny.maven.core.common.domain;

import lombok.Data;

import java.util.Collection;

/**
 * @author SUNNY
 * @description: 用户会话对象
 * @create: 2022-09-22 11:22
 */
@Data
public class UserDto {
    private String username;
    private Collection<String> authorities;

    public static UserDto createUserDto() {
        return new UserDto();
    }
}
