package com.sunny.maven.middleserver.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author SUNNY
 * @description: 统一管理Bean配置类
 * @create: 2022-04-13 17:17
 */
@Configuration
public class Oauth2Config {
    /**
     * 加密方式 BCrypt
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }
}
