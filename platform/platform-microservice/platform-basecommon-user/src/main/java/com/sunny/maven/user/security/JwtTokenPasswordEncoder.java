package com.sunny.maven.user.security;

import com.sunny.maven.user.security.cryptoutil.PasswordEncode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @author SUNNY
 * @description: 密码处理
 * @create: 2022-09-14 18:15
 */
@Slf4j
@Component
public class JwtTokenPasswordEncoder implements PasswordEncoder {
    private PasswordEncode passwordEncode;
    @Override
    public String encode(CharSequence rawPassword) {
        return rawPassword.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (rawPassword == null) {
            throw new IllegalArgumentException("rawPassword cannot be null");
        }
        if (!StringUtils.hasText(encodedPassword)) {
            throw new IllegalArgumentException("encodedPassword is null");
        }
        String pwd = "";
        try {
            pwd = passwordEncode.passwordEncode(rawPassword.toString());
        } catch (Exception ignored) {
        }
        return encodedPassword.contentEquals(pwd);
    }
    @Autowired
    public JwtTokenPasswordEncoder(PasswordEncode passwordEncode) {
        this.passwordEncode = passwordEncode;
    }
}
