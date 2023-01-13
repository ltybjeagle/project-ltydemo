package com.sunny.maven.core.utils.crypto;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;

/**
 * @author SUNNY
 * @description: MD5工具类
 * @create: 2022-06-16 21:45
 */
@Slf4j
public class Md5Hash {
    private static final String KEY_MD5 = "MD5";

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
    private Md5Hash() {
        /*
         * valueOf会使用缓存池中的对象，多次调用会取得同一个对象的引用
         * Integer a = 123;  ==> Integer a = Integer.valueOf(123)
         */
        Integer a = 123;
        Integer b = 123;
        log.info(" a == b : {}", a.equals(b));
    }
}
