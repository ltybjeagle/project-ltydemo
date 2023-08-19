package com.sunny.maven.middle.authentication.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author SUNNY
 * @description: 自定义Spring Security(基于内存管理用户)
 * @create: 2023/8/10 18:44
 */
@Slf4j
@Configuration
@ConditionalOnProperty(
        name = "sunny.security.authentication.type",
        havingValue = "memory",
        matchIfMissing = true)
public class MemoryWebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().
                withUser("root").
                password("$2a$10$UhHxgsyD6eIwFkHEEBxXRuuxMppJOmcukEcSdbYU30p8TgnHuXI42").
                roles("ADMIN", "DBA").
                and().
                withUser("admin").
                password("$2a$10$UhHxgsyD6eIwFkHEEBxXRuuxMppJOmcukEcSdbYU30p8TgnHuXI42").
                roles("ADMIN", "USER").
                and().
                withUser("sunny").
                password("$2a$10$UhHxgsyD6eIwFkHEEBxXRuuxMppJOmcukEcSdbYU30p8TgnHuXI42").
                roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().
                antMatchers("/admin/**").
                hasRole("ADMIN").
                antMatchers("/user/**").
                access("hasAnyRole('ADMIN', 'USER')").
                antMatchers("/db/**").
                access("hasRole('ADMIN') and hasRole('DBA')").
                anyRequest().
                authenticated().
                and().
                formLogin().
                loginProcessingUrl("/login").
                usernameParameter("name").
                passwordParameter("passwd").
                successHandler((request, response, authentication) -> {
                    Object principal = authentication.getPrincipal();
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    response.setStatus(200);
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", 200);
                    map.put("codeMsg", "登录成功!");
                    map.put("success", true);
                    map.put("data", principal);
                    ObjectMapper om = new ObjectMapper();
                    out.write(om.writeValueAsString(map));
                    out.flush();
                    out.close();
                }).failureHandler((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    PrintWriter out = response.getWriter();
                    response.setStatus(401);
                    Map<String, Object> map = new HashMap<>();
                    map.put("code", 401);
                    map.put("success", true);
                    map.put("data", null);
                    if (exception instanceof LockedException) {
                        map.put("codeMsg", "账户被锁定,登录失败!");
                    } else if (exception instanceof BadCredentialsException) {
                        map.put("codeMsg", "用户名或密码输入错误,登录失败!");
                    } else if (exception instanceof DisabledException) {
                        map.put("codeMsg", "账户被禁用,登录失败!");
                    } else if (exception instanceof AccountExpiredException) {
                        map.put("codeMsg", "账户已过期,登录失败!");
                    } else if (exception instanceof CredentialsExpiredException) {
                        map.put("codeMsg", "密码已过期,登录失败!");
                    } else {
                        map.put("codeMsg", "登录失败!");
                    }
                    ObjectMapper om = new ObjectMapper();
                    out.write(om.writeValueAsString(map));
                    out.flush();
                    out.close();
                }).
                permitAll().
                and().
                logout().
                logoutUrl("/logout").
                clearAuthentication(true).
                invalidateHttpSession(true).
                addLogoutHandler((request, response, authentication) -> {
                    log.info("退出系统，清理资源");
                }).logoutSuccessHandler((request, response, authentication) -> {
                    log.info("退出成功，响应信息");
                }).
                and().
                csrf().
                disable();
    }

    public MemoryWebSecurityConfig() {
        log.info("MemoryWebSecurityConfig load..............");
    }
}
