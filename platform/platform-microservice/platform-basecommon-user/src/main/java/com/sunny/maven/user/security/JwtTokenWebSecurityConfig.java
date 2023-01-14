package com.sunny.maven.user.security;

import com.sunny.maven.cache.service.ICacheFacadeService;
import com.sunny.maven.cache.template.CacheTemplate;
import com.sunny.maven.core.common.constants.CommonConstant;
import com.sunny.maven.security.JwtTokenAuthWebSecurityConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author SUNNY
 * @description: config配置
 * @create: 2022-09-08 18:06
 */
@Slf4j
@Configuration
@Order(0)
public class JwtTokenWebSecurityConfig extends WebSecurityConfigurerAdapter {
    private JwtTokenAuthWebSecurityConfig jwtTokenAuthWebSecurityConfig;
    /**
     * 密码管理工具
     */
    private PasswordEncoder passwordEncoder;
    /**
     * 用户服务类
     */
    private UserDetailsService userDetailsService;
    /**
     * 认证密钥
     */
    private JwtRsaKeyProperties jwtRsaKeyProperties;
    private ICacheFacadeService cacheRedisService;

    /**
     * 配置设置，设置退出的地址和token
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http = jwtTokenAuthWebSecurityConfig.getHttpSecurity();
        log.info("JwtTokenWebSecurityConfig.HttpSecurity() => {}-{}", http, jwtTokenAuthWebSecurityConfig.authenticationManagerBean());
        // 关闭CSRF
        http.csrf().disable().
                authorizeRequests().
                antMatchers("/user/auth/**", "/auth/**", "/login").permitAll().
                anyRequest().authenticated().
                and().formLogin().and().
                addFilter(new JwtTokenLoginFilter(jwtTokenAuthWebSecurityConfig.authenticationManagerBean(), jwtRsaKeyProperties, cacheTokenTemplate())).
//                addFilter(new JwtTokenAuthenticationFilter(authenticationManager())).
                // 未授权处理
                exceptionHandling().authenticationEntryPoint(new JwtTokenUnauthorizedEntryPoint());
    }

    @Bean
    public CacheTemplate cacheTokenTemplate() {
        return new CacheTemplate(CommonConstant.Redis.TOKEN_CACHE_KEY, cacheRedisService);
    }

    /**
     * 密码处理
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    /**
     * 构造函数
     */
    @Autowired
    public JwtTokenWebSecurityConfig(PasswordEncoder passwordEncoder, UserDetailsService userDetailsService,
                                     JwtRsaKeyProperties jwtRsaKeyProperties, ICacheFacadeService cacheRedisService,
                                     JwtTokenAuthWebSecurityConfig jwtTokenAuthWebSecurityConfig) {
        log.info("init JwtTokenWebSecurityConfig...");
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtRsaKeyProperties = jwtRsaKeyProperties;
        this.cacheRedisService = cacheRedisService;
        this.jwtTokenAuthWebSecurityConfig = jwtTokenAuthWebSecurityConfig;
    }

}
