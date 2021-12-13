package com.sunny.maven.util.encryption;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author SUNNY
 * @description: HMAC工具类
 * @create: 2021-11-17 17:12
 */
public class HmacUtil {
    public static final String KEY_MAC = "HmacMD5";

    /***
     * 初始化HMAC密钥
     * @return String
     * @throws Exception 异常
     */
    public static String initMacKey() throws Exception{
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64Util.encryptBase64(secretKey.getEncoded());
    }

    /**
     * HMAC加密
     * @param data 原文
     * @param key 密钥t
     * @return byte[]
     * @throws Exception 异常
     */
    public static byte[] encryptHmac(byte[] data, String key) throws Exception{
        SecretKey secretKey = new SecretKeySpec(Base64Util.decryBase64(key), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal();
    }

    /**
     * 私有构造函数
     */
    private HmacUtil() {}
}
