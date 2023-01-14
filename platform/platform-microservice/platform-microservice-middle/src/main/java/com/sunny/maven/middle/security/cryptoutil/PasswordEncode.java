package com.sunny.maven.middle.security.cryptoutil;

/**
 * @author SUNNY
 * @description: 密码处理工具
 * @create: 2022-09-16 15:33
 */
public interface PasswordEncode {
    /**
     * 密码编码
     * @param password 密码
     * @return  String
     * @throws Exception 异常
     */
    String passwordEncode(String password) throws Exception;
}
