package com.sunny.maven.middle.security.cryptoutil;

import com.sunny.maven.core.utils.crypto.AesUtils;
import com.sunny.maven.core.utils.crypto.Base64Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: 密码处理工具——AES
 * @create: 2022-09-16 15:31
 */
@Component
public class PasswordEncodeByAes implements PasswordEncode {
    @Value("${platform.user.password.aes_key:abcdefghijklmn12}")
    private String AES_KEY;
    @Value("${platform.user.password.aes_iv:abcdefghijklmn12}")
    private String AES_IV;

    /**
     * 密码编码
     * @param password 密码
     * @return  String
     * @throws Exception 异常
     */
    @Override
    public String passwordEncode(String password) throws Exception {
        return Base64Utils.encryptBase64(
                AesUtils.AES_CBC_Encrypt(password.getBytes(), AES_KEY.getBytes(), AES_IV.getBytes()));
    }
}
