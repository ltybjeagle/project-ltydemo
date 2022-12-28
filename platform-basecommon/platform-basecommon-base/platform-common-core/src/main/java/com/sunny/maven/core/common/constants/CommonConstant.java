package com.sunny.maven.core.common.constants;

import java.util.Arrays;
import java.util.List;

/**
 * @author SUNNY
 * @description: 公共常量定义
 * @create: 2022-09-21 10:57
 */
public class CommonConstant {
    /**
     * 登陆用户上下文
     */
    public static final String USER_CONTEXT = "USER_CONTEXT";
    public static final String LOGIN_AUTH_PATH = "/auth/login";
    public static final int EXPIRATION_TIME = 30;
    public static final long SESSION_REDIS_TIME = 2 * EXPIRATION_TIME;

    public static final List<String> REQUEST_PREFIX_WHILE = Arrays.asList("/user/auth/", "/config/test");
    /**
     * 常用Redis存储KEY
     */
    public interface Redis {
        String TOKEN_CACHE_KEY = "PLATFORM.CACHE.TOKEN.USER:";
    }
}
