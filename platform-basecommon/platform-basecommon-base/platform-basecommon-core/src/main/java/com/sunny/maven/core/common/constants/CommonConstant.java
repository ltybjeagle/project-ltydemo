package com.sunny.maven.core.common.constants;

/**
 * @author SUNNY
 * @description: 公共常量定义
 * @create: 2022-09-21 10:57
 */
public class CommonConstant {
    public static final long SESSION_REDIS_TIME = 2 * 30;
    public static final int EXPIRATION_TIME = 30;
    /**
     * 常用Redis存储KEY
     */
    public interface Redis {
        String TOKEN_CACHE_KEY = "PLATFORM.CACHE.TOKEN.USER:";
    }
}
