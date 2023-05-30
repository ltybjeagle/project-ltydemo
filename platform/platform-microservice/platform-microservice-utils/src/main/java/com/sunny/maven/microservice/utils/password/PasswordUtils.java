package com.sunny.maven.microservice.utils.password;

import com.sunny.maven.microservice.utils.md5.Md5Hash;

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
        if (password == null || password.trim().isEmpty()) return password;
        for (int i = 0; i < 5; i++) {
            password = Md5Hash.md5Java(password);
        }
        return password;
    }
}
