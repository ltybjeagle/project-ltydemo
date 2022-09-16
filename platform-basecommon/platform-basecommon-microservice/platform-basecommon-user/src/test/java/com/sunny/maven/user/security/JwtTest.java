package com.sunny.maven.user.security;

import org.junit.Test;

/**
 * @author SUNNY
 * @description: JwtTest
 * @create: 2022-09-10 16:29
 */
public class JwtTest {

    private String privateKey = "E:/tools/auth_key/id_key_rsa";
    private String publicKey = "E:/tools/auth_key/id_key_rsa.pub";

    @Test
    public void jwtTest() throws Exception {
        JwtRsaUtil.generateKey(publicKey, privateKey, "PlatFormSecretSafe", 1024);
    }
}
