package com.sunny.maven.holder;

/**
 * @author SUNNY
 * @description: 请求对象类型
 * @create: 2021-11-17 11:44
 */
public class RequestHolderUtil {
    /**
     * 请求对象类型会话
     */
    private static final ThreadLocal<String> requestHolder = new ThreadLocal<>();

    /**
     * 设置请求类型
     * @param language 类型（中文、英文）
     */
    public static void addLang(String language) {
        requestHolder.set(language);
    }

    /**
     * 获取类型会话
     * @return String
     */
    public static String getLang() {
        return requestHolder.get();
    }

    /**
     * 清楚类型会话
     */
    public static void remove() {
        requestHolder.remove();
    }

    /**
     * 私有构造
     */
    private RequestHolderUtil() {}
}
