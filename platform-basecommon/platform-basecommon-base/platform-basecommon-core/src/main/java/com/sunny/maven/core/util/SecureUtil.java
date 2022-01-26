package com.sunny.maven.core.util;

import com.sunny.maven.core.common.context.UserInfoContextHolder;

import java.util.Map;

/**
 * @author SUNNY
 * @description: session会话工具类
 * @create: 2022-01-17 14:42
 */
public final class SecureUtil {

    /**
     * 获取当前登录用户TokenId
     * @return String
     */
    public static final String getTokenId() {
        return UserInfoContextHolder.getContext().getTokenId();
    }
    /**
     * 获取当前登录用户
     * @return Map
     */
    public static final Map<String, Object> getUserDto() {
        return UserInfoContextHolder.getContext().getUserDto();
    }
    /**
     * 私有构造函数
     */
    private SecureUtil() {}
}
