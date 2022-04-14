package com.sunny.maven.middleserver.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author SUNNY
 * @description: 统一管理Bean配置类
 * @create: 2022-04-13 17:17
 */
@Configuration
public class Oauth2Config {

    private RedisConnectionFactory redisConnectionFactory;
    /**
     * 加密方式 BCrypt
     * @return PasswordEncoder
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return  new BCryptPasswordEncoder();
    }

    /**
     * Redis令牌管理
     * 步骤：
     * 1.启动redis
     * 2.添加redis依赖
     * 3.添加redis 依赖后, 容器就会有 RedisConnectionFactory 实例
     * @return
     */
    @Bean
    public TokenStore redisTokenStore(){
        return  new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * 构造函数
     * @param redisConnectionFactory
     */
    @Autowired
    public Oauth2Config(RedisConnectionFactory redisConnectionFactory) {
        this.redisConnectionFactory = redisConnectionFactory;
    }
}
