package com.sunny.maven.user.security;

import com.sunny.maven.cache.service.ICacheFacadeService;
import com.sunny.maven.cache.template.CacheTemplate;
import com.sunny.maven.core.common.constants.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author SUNNY
 * @description: config配置
 * @create: 2022-09-08 18:06
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class JwtTokenWebSecurityConfig extends WebSecurityConfigurerAdapter {
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
        // 关闭CSRF
        http.csrf().disable().
                authorizeRequests().
                antMatchers("/user/auth/**", "/auth/**", "/login").permitAll().
                anyRequest().authenticated().
                and().formLogin().and().
                addFilter(new JwtTokenLoginFilter(authenticationManager(), jwtRsaKeyProperties, cacheTokenTemplate())).
                addFilter(new JwtTokenAuthenticationFilter(authenticationManager(), jwtRsaKeyProperties,
                        cacheTokenTemplate())).
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
                                     JwtRsaKeyProperties jwtRsaKeyProperties, ICacheFacadeService cacheRedisService) {
        this.passwordEncoder = passwordEncoder;
        this.userDetailsService = userDetailsService;
        this.jwtRsaKeyProperties = jwtRsaKeyProperties;
        this.cacheRedisService = cacheRedisService;
    }

}