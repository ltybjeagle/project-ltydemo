package com.sunny.maven.core.demo.utils.crypto;

import com.sunny.maven.core.utils.crypto.AesUtils;
import com.sunny.maven.core.utils.crypto.Base64Utils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author SUNNY
 * @description: AesUtilsDemoTest
 * @create: 2023-04-05 22:29
 */
@Slf4j
public class AesUtilsDemoTest {

    private String AES_KEY = "abcdefghijklmn12";
    private String AES_IV = "abcdefghijklmn12";

    @Test
    public void AES_CBC_Encrypt_test() throws Exception {
        String content = "1Qa1234567890";
        String result = Base64Utils.encryptBase64(
                AesUtils.AES_CBC_Encrypt(content.getBytes(), AES_KEY.getBytes(), AES_IV.getBytes()));
        log.info("result==>>{}", result);
    }
}
