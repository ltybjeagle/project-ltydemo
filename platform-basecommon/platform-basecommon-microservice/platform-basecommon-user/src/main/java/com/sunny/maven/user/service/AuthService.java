package com.sunny.maven.user.service;

/**
 * @author SUNNY
 * @description: 认证鉴权
 * @create: 2022-09-23 14:06
 */
public interface AuthService {
    /**
     * 认证函数
     * @param tokenId
     * @return
     */
    String verifyToken(String tokenId);
}
