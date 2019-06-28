package com.liuty.maven.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @描述: 自定义安全策略
 *
 * @创建人: Sunny
 * @创建时间: 2019/6/28
 */
@Configuration
@EnableWebSecurity
public class DemoCustomSecurityConfiguration {

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return null;
    }

    @Bean
    public AccessDecisionManager accessDecisionManager() throws Exception {
        return null;
    }

    @Bean
    public SecurityFilterChain securityFilterChain() throws Exception {
        return null;
    }
}
