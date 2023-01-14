package com.sunny.maven.middle.security;

import lombok.Data;

import java.util.Date;

/**
 * @author SUNNY
 * @description: 认证信息
 * @create: 2022-09-10 14:10
 */
@Data
public class JwtTokenPayload<T> {
    private String id;
    private T userInfo;
    private Date expiration;
}
