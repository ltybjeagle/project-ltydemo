package com.sunny.maven.microservice.authentication.jwt.password;

import com.sunny.maven.microservice.authentication.jwt.password.annotation.SPIPasswordCodec;
import com.sunny.maven.microservice.authentication.jwt.password.codec.PasswordCodec;
import org.springframework.core.env.Environment;
import org.springframework.core.io.support.SpringFactoriesLoader;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author SUNNY
 * @description: 密码处理
 * @create: 2023-04-03 22:13
 */
public class JwtPasswordEncoder implements PasswordEncoder {
    private PasswordCodec passwordCodec;
    private final Map<String, PasswordCodec> cachedInstances = new ConcurrentHashMap<>();
    private Environment env;

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
            pwd = passwordCodec.passwordEncode(rawPassword.toString());
        } catch (Exception ignored) {
        }
        return encodedPassword.contentEquals(pwd);
    }

    public JwtPasswordEncoder(String passwordCodecType, Environment env) {
        List<PasswordCodec> passwordCodecs = SpringFactoriesLoader.loadFactories(PasswordCodec.class,
                Thread.currentThread().getContextClassLoader());
        passwordCodecs.forEach(passwordCodec -> {
            SPIPasswordCodec type = passwordCodec.getClass().getAnnotation(SPIPasswordCodec.class);
            passwordCodec.init(env);
            cachedInstances.put(type.value(), passwordCodec);
        });
        this.passwordCodec = cachedInstances.get(passwordCodecType);
    }
}
