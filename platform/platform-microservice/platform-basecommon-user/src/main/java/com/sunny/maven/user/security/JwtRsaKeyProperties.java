package com.sunny.maven.user.security;

import com.sunny.maven.user.security.JwtRsaUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.net.URL;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author SUNNY
 * @description: RSA公私钥配置
 * @create: 2022-09-10 22:35
 */
@Slf4j
@Data
@Configuration
public class JwtRsaKeyProperties {
    private PublicKey publicKey;
    private PrivateKey privateKey;

    /**
     * 系统启动的时候触发
     * @throws Exception
     */
    @PostConstruct
    public void createRsaKey() throws Exception {
        log.info("JwtRsaKeyProperties init..........");
        final URL pubKeyFileUrl = this.getClass().getClassLoader().getResource("id_key_rsa.pub");
        log.info("pubKeyFileUrl={}", pubKeyFileUrl.getPath());
        publicKey = JwtRsaUtil.getPublicKey(pubKeyFileUrl.getPath());
        final URL priKeyFileUrl = this.getClass().getClassLoader().getResource("id_key_rsa");
        log.info("priKeyFileUrl={}", priKeyFileUrl.getPath());
        privateKey = JwtRsaUtil.getPrivateKey(priKeyFileUrl.getPath());
    }
}
