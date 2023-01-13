package com.sunny.maven.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @author SUNNY
 * @description: config配置
 * @create: 2022-09-08 18:06
 */
@Slf4j
@Configuration
@Order(-1)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JwtTokenAuthWebSecurityConfig extends WebSecurityConfigurerAdapter {

    private Jackson2JsonRedisSerializer jackson2JsonRedisSerializer;

    public HttpSecurity getHttpSecurity() throws Exception {
        return super.getHttp();
    }
    /**
     * 配置设置
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("JwtTokenAuthWebSecurityConfig.HttpSecurity() => {}-{}", http, authenticationManagerBean());
        http.addFilter(new JwtTokenAuthenticationFilter(authenticationManagerBean(), jackson2JsonRedisSerializer));
    }
    @Autowired
    public JwtTokenAuthWebSecurityConfig(Jackson2JsonRedisSerializer jackson2JsonRedisSerializer) {
        log.info("init JwtTokenAuthWebSecurityConfig...");
        this.jackson2JsonRedisSerializer = jackson2JsonRedisSerializer;
    }
}
