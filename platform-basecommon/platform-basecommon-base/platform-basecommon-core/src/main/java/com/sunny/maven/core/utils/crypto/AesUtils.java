package com.sunny.maven.core.utils.crypto;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author SUNNY
 * @description: AES加密
 * @create: 2022-09-16 13:59
 */
public class AesUtils {

    public static byte[] AES_CBC_Encrypt(byte[] content, byte[] keyBytes, byte[] iv) throws Exception {
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,key, new IvParameterSpec(iv));
        byte[] result = cipher.doFinal(content);
        return result;
    }

    public static byte[] AES_CBC_Decrypt(byte[] content, byte[] keyBytes, byte[] iv) throws Exception {
        SecretKeySpec key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.DECRYPT_MODE,key, new IvParameterSpec(iv));
        byte[] result = cipher.doFinal(content);
        return result;
    }
}
