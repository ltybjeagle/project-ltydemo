package com.sunny.maven.core.utils.date;

import com.sunny.maven.core.utils.crypto.AesUtils;
import com.sunny.maven.core.utils.crypto.Base64Utils;
import org.junit.Test;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

/**
 * @author SUNNY
 * @description: 日期对象测试
 * @create: 2022-09-10 14:46
 */
public class DateTest {
    private String AES_key = "abcdefghijklmn12";
    private String AES_iv = "abcdefghijklmn12";

    @Test
    public void dateTest() throws Exception {
        System.out.println(Date.from(LocalDateTime.now().plusMinutes(30).atZone(ZoneId.systemDefault()).toInstant()));
        System.out.println(Base64Utils.encryptBase64(
                AesUtils.AES_CBC_Encrypt("1Qa1234567890".getBytes(), AES_key.getBytes(), AES_iv.getBytes())));
        System.out.println(new String(AesUtils.AES_CBC_Decrypt(
                Base64Utils.decryBase64("XvPs37UMhY4hg53WvLa8vz+7QWHMOZUsY7gAjO6aHak="),
                AES_key.getBytes(), AES_iv.getBytes())));
    }
}
