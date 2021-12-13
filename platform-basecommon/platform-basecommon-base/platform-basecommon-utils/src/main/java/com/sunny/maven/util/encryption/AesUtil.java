package com.sunny.maven.util.encryption;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author SUNNY
 * @description: AES工具类
 * @create: 2021-11-17 15:42
 */
public class AesUtil {
    public static final String ALGORITHM = "AES";
    /**
     * AES/CBC/NOPaddin
     * AES 默认模式
     * 使用CBC模式, 在初始化Cipher对象时, 需要增加参数,
     *      初始化向量IV : IvParameterSpec iv = new IvParameterSpec(key.getBytes());
     * NOPadding: 使用NOPadding模式时, 原文长度必须是8byte的整数倍
     */
    public static final String TRANSFORMATION = "AES/CBC/NOPadding";
    public static final String KEY = "1234567812345678";

    /**
     * 加密
     * @param original 需要加密的参数（注意必须是16位）
     * @return String
     * @throws Exception 异常
     */
    public static String encryptByAes(String original) throws Exception {
        // 获取Cipher
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        // 生成密钥
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        // 指定模式(加密)和密钥
        // 创建初始化向量
        IvParameterSpec iv = new IvParameterSpec(KEY.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, iv);
        // 加密
        byte[] bytes = cipher.doFinal(original.getBytes());
        return Base64Util.encryptBase64(bytes);
    }

    /**
     * 解密
     * @param encrypted 需要解密的参数
     * @return String
     * @throws Exception 异常
     */
    public static String decryptByAes(String encrypted) throws Exception {
        // 获取Cipher
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        // 生成密钥
        SecretKeySpec keySpec = new SecretKeySpec(KEY.getBytes(), ALGORITHM);
        // 指定模式(解密)和密钥
        // 创建初始化向量
        IvParameterSpec iv = new IvParameterSpec(KEY.getBytes());
        cipher.init(Cipher.DECRYPT_MODE, keySpec, iv);
        // 解密
        byte[] bytes = cipher.doFinal(Base64Util.decryBase64(encrypted));
        return new String(bytes);
    }

    /**
     * 私有构造函数
     */
    private AesUtil() {}
}
