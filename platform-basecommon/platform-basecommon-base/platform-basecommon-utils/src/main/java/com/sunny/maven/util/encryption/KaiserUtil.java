package com.sunny.maven.util.encryption;

/**
 * @author SUNNY
 * @description: Kaisa工具类
 * @create: 2021-11-17 17:18
 */
public class KaiserUtil {
    /***
     * 使用凯撒加密方式加密数据
     * @param original 原文
     * @param key 密钥
     * @return 加密后的字符
     */
    private static String encryptKaiser(String original, int key) {
        // 将字符串转换为数组
        char[] chars = original.toCharArray();
        StringBuffer buffer = new StringBuffer();
        // 遍历数组
        for (char aChar : chars) {
            // 获取字符的ASCII编码
            int asciiCode = aChar;
            // 偏移数据
            asciiCode += key;
            // 将偏移后的数据转为字符
            char result = (char) asciiCode;
            // 拼接数据
            buffer.append(result);
        }
        return buffer.toString();
    }

    /**
     * 使用凯撒加密方式解密数据
     * @param encryptedData :密文
     * @param key           :密钥
     * @return : 源数据
     */
    private static String decryptKaiser(String encryptedData, int key) {
        // 将字符串转为字符数组
        char[] chars = encryptedData.toCharArray();
        StringBuilder sb = new StringBuilder();
        // 遍历数组
        for (char aChar : chars) {
            // 获取字符的ASCII编码
            int asciiCode = aChar;
            // 偏移数据
            asciiCode -= key;
            // 将偏移后的数据转为字符
            char result = (char) asciiCode;
            // 拼接数据
            sb.append(result);
        }
        return sb.toString();
    }
}
