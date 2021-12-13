package com.sunny.maven.util.encryption;

import java.util.Base64;

/**
 * @author SUNNY
 * @description: Base64工具类
 * @create: 2021-11-17 15:10
 */
public class Base64Util {
    /**
     * BASE64解密
     * @param key 密文
     * @return byte[]
     * @throws Exception 异常
     */
    public static byte[] decryBase64(String key) throws Exception {
        return (Base64.getDecoder()).decode(key);
    }

    /**
     * BASE64加密
     * @param key 原文
     * @return String
     * @throws Exception 异常
     */
    public static String encryptBase64(byte[] key) throws Exception {
        return (Base64.getEncoder()).encodeToString(key);
    }

    /**
     * 私有构造函数
     */
    private Base64Util() {}
}
