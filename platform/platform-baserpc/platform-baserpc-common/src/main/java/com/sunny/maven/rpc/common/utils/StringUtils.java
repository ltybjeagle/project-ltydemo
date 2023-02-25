package com.sunny.maven.rpc.common.utils;

/**
 * @author SUNNY
 * @description: 字符串工具类
 * @create: 2023-02-24 16:26
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
