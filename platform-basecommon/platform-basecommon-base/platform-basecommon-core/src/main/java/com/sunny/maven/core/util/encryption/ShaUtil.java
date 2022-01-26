package com.sunny.maven.core.util.encryption;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author SUNNY
 * @description: SHA工具类
 * @create: 2021-11-17 17:25
 */
public class ShaUtil {
    public static final String KEY_SHA = "SHA";
    public static final String ALGORITHM = "SHA-256";

    /***
     * SHA加密（比MD5更安全）
     * @param data 原文
     * @return byte[]
     * @throws Exception 异常
     */
    public static byte[] encryptSha(byte[] data) throws Exception {
        MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
        sha.update(data);
        return sha.digest();
    }

    /***
     * SHA加密
     * @param content 原文
     * @return String
     */
    public static String shaEncrypt(final String content) {
        try {
            MessageDigest sha = MessageDigest.getInstance(KEY_SHA);
            byte[] sha_byte = sha.digest(content.getBytes());
            StringBuffer hexValue = new StringBuffer();
            /*
             * 将其中的每个字节转成十六进制字符串：
             *      byte类型的数据最高位是符号位，通过和0xff进行与操作，转换为int类型的正整数。
             */
            for (byte b : sha_byte) {
                String toHexString = Integer.toHexString(b & 0xff);
                hexValue.append(toHexString.length() == 1 ? "0" + toHexString : toHexString);
            }
            return hexValue.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * SHA-256加密
     * @param sourceStr 原文
     * @return String
     */
    public static String sha256Encrypt(String sourceStr) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        if (null != md) {
            md.update(sourceStr.getBytes());
            return getDigestStr(md.digest());
        }
        return null;
    }

    private static String getDigestStr(byte[] origBytes) {
        String tempStr = null;
        StringBuilder stb = new StringBuilder();
        for (byte origByte : origBytes) {
            tempStr = Integer.toHexString(origByte & 0xff);
            if (tempStr.length() == 1) {
                stb.append("0");
            }
            stb.append(tempStr);

        }
        return stb.toString();
    }
}
