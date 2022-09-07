package com.sunny.maven.core.utils.psswd;

import com.sunny.maven.core.utils.md5.Md5Hash;

import java.util.Objects;

/**
 * @author SUNNY
 * @description: 密码的加密
 * @create: 2022-06-16 21:50
 */
public class PasswordUtils {

    /**
     * 加密
     * @param password 密码原文
     * @return String
     */
    public static String getPassWord(String password) {
        byte[] pwd = password.getBytes();
        try {
            pwd = Md5Hash.encryptMd5(password.getBytes());
        } catch (Exception e) {}
        return Objects.isNull(pwd) ? password : new String(pwd);
    }
}
