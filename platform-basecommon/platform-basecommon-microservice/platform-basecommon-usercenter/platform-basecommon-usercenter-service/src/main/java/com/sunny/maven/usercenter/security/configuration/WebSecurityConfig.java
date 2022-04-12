package com.sunny.maven.usercenter.security.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.servlet.http.HttpServletResponse;

/**
 * @author SUNNY
 * @description: Security配置类
 * @create: 2022-02-24 11:26
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService oauth2UserDetailService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable() // 关闭CSRF
                .exceptionHandling()
                .authenticationEntryPoint((request, response, authException)
                        -> response.sendError(HttpServletResponse.SC_UNAUTHORIZED))
                .and()
                .authorizeRequests()
                .antMatchers("/**")
                .authenticated()
                .and()
                .httpBasic();
}

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(oauth2UserDetailService)
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    /**
     * 构造函数
     * @param oauth2UserDetailService
     */
    @Autowired
    public WebSecurityConfig(UserDetailsService oauth2UserDetailService) {
        this.oauth2UserDetailService = oauth2UserDetailService;
    }
}
