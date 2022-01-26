package com.sunny.maven.core.util.encryption;

import com.sunny.maven.core.common.enums.ReturnEnum;
import com.sunny.maven.core.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author SUNNY
 * @description: AES工具类
 * @create: 2021-11-17 15:42
 */
public class AesUtil {
    private static final Logger logger = LoggerFactory.getLogger(AesUtil.class);
    public static final String ALGORITHM = "AES";
    /**
     * AES/CBC/NOPaddin
     * AES 默认模式
     * 使用CBC模式, 在初始化Cipher对象时, 需要增加参数,
     *      初始化向量IV : IvParameterSpec iv = new IvParameterSpec(key.getBytes());
     * NOPadding: 使用NOPadding模式时, 原文长度必须是8byte的整数倍
     */
    public static final String TRANSFORMATION = "AES/CBC/PKCS5Padding";
    public static final String KEY = "abcdefghijklmn12";

    /**
     * 加密
     * @param original 需要加密的参数（注意必须是16位）
     * @return String
     * @throws Exception 异常
     */
    public static String encryptByAes(String original) {
        try {
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
        } catch (Exception ex) {
            logger.error("加密失败：{}", ex.getMessage());
            throw new AppException.AppExceptionBuilder().code(ReturnEnum.AES590.getMsgCode()).build();
        }
    }

    /**
     * 解密
     * @param encrypted 需要解密的参数
     * @return String
     * @throws Exception 异常
     */
    public static String decryptByAes(String encrypted) {
        try {
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
        } catch (Exception ex) {
            logger.error("解密密码失败：{}", ex.getMessage());
            throw new AppException.AppExceptionBuilder().code(ReturnEnum.AES591.getMsgCode()).build();
        }
    }

    /**
     * 私有构造函数
     */
    private AesUtil() {}
}
