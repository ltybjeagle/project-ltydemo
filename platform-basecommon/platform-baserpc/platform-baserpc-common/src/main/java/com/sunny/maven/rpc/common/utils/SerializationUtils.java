package com.sunny.maven.rpc.common.utils;

import java.util.stream.IntStream;

/**
 * @author SUNNY
 * @description: 序列化时针对消息头序列化类型的操作
 * @create: 2022-12-28 13:48
 */
public class SerializationUtils {
    private static final String PADDING_STRING = "0";
    /**
     * 约定序列化类型最大长度为16
     */
    public static final int MAX_SERIALIZATION_TYPE_COUNT = 16;

    /**
     * 为长度不足16的字符串后面补0
     * @param str 原始字符串
     * @return 补0后的字符串
     */
    public static String paddingString(String str) {
        str = transNullToEmpty(str);
        if (str.length() >= MAX_SERIALIZATION_TYPE_COUNT) {
            return str;
        }
        int paddingCount = MAX_SERIALIZATION_TYPE_COUNT - str.length();
        StringBuilder paddingString = new StringBuilder(str);
        IntStream.range(0, paddingCount).forEach(i -> paddingString.append(PADDING_STRING));
        return paddingString.toString();
    }

    /**
     * 字符串去0 操作
     * @param str
     * @return
     */
    public static String subString(String str) {
        str = transNullToEmpty(str);
        return str.replace(PADDING_STRING, "");
    }

    public static String transNullToEmpty(String str) {
        return str == null ? "" : str;
    }
}
