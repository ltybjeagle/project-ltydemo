package com.sunny.maven.util.encryption;

import java.security.MessageDigest;

/**
 * @author SUNNY
 * @description: MD5工具类
 * @create: 2021-11-17 15:22
 */
public class Md5Util {
    public static final String KEY_MD5 = "MD5";

    /**
     * MD5加密（生成唯一的MD5值）
     * @param data 原文
     * @return byte[]
     * @throws Exception 异常
     */
    public static byte[] encryptMd5(byte[] data) throws Exception {
        MessageDigest md5 = MessageDigest.getInstance(KEY_MD5);
        md5.update(data);
        return md5.digest();
    }

    /**
     * 私有构造函数
     */
    private Md5Util() {}
}
