package com.sunny.maven.middle;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author SUNNY
 * @description:
 * @create: 2023/8/11 17:07
 */
@Slf4j
public class PasswordEncoderTest {

    @Test
    public void passwordEncoderTest() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        log.info("123456 BCrypt {}", encoder.encode("123456"));
        // $2a$10$UhHxgsyD6eIwFkHEEBxXRuuxMppJOmcukEcSdbYU30p8TgnHuXI42
    }
}
