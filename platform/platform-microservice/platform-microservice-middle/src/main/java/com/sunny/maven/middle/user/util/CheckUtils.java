package com.sunny.maven.middle.user.util;

import com.sunny.maven.core.exception.CheckException;

import java.util.stream.Stream;

/**
 * @author SUNNY
 * @description: 自定义校验工具类
 * @create: 2022-09-29 16:04
 */
public class CheckUtils {
    private static final String[] INVALID_NEWS = {"admin", "guanliyuan"};

    /**
     * 校验名称，不成功抛出校验异常
     * @param value
     */
    public static void checkUserCode(String value) {
        Stream.of(INVALID_NEWS).
                filter(userCode -> userCode.equalsIgnoreCase(value)).
                findAny().
                ifPresent(userCode -> {
                    throw new CheckException("userCode", value);
                });
    }
}
