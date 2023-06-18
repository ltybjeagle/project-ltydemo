package com.sunny.maven.middle.authentication.jwt.password.codec.aes;

import com.sunny.maven.middle.authentication.jwt.password.annotation.SPIPasswordCodec;
import com.sunny.maven.middle.authentication.jwt.password.codec.PasswordCodec;
import com.sunny.maven.middle.authentication.jwt.password.utils.Base64Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author SUNNY
 * @description: 密码处理工具——AES
 * @create: 2023-04-03 22:18
 */
@SPIPasswordCodec(value = "aes")
public class AesPasswordCodec implements PasswordCodec {

    private String AES_KEY;
    private String AES_IV;

    private final String DEFAULT_AES_KEY = "abcdefghijklmn12";
    private final String DEFAULT_AES_IV = "abcdefghijklmn12";
    /**
     * 密码编码
     * @param password 密码
     * @return  String
     * @throws Exception 异常
     */
    @Override
    public String passwordEncode(String password) throws Exception {
        SecretKeySpec key = new SecretKeySpec(AES_KEY.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(Cipher.ENCRYPT_MODE,key, new IvParameterSpec(AES_IV.getBytes()));
        byte[] result = cipher.doFinal(password.getBytes());
        return Base64Utils.encryptBase64(result);
    }
    /**
     * 初始化
     * @param env
     */
    @Override
    public void init(Environment env) {
        String key = env.getProperty("platform.microservice.authentication.password.codec.ase.aes_key");
        AES_KEY = StringUtils.isEmpty(key) ? DEFAULT_AES_KEY : key;
        String iv = env.getProperty("platform.microservice.authentication.password.codec.ase.aes_iv");
        AES_IV = StringUtils.isEmpty(iv) ? DEFAULT_AES_IV : iv;
    }
}
