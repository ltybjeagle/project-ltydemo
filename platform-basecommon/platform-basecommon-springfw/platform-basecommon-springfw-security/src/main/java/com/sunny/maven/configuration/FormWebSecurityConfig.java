package com.sunny.maven.configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/8/16 22:28
 * @description：表单认证
 * @modified By：
 * @version: 1.0.0
 */
@EnableWebSecurity
public class FormWebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 表单认证
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/myLogin.html")
                // 指定处理登陆请求的路径
                .loginProcessingUrl("/login")
                // 指定登陆成功时的处理逻辑
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest
                            , HttpServletResponse httpServletResponse
                            , Authentication authentication) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=UTF-8");
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"error_code\":\"0\",\"message\":\"欢迎登陆系统\"}");
                    }
                })
                // 指定登陆失败时的处理逻辑
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest
                            , HttpServletResponse httpServletResponse
                            , AuthenticationException e) throws IOException, ServletException {
                        httpServletResponse.setContentType("application/json;charset=UTF-8");
                        httpServletResponse.setStatus(401);
                        PrintWriter out = httpServletResponse.getWriter();
                        out.write("{\"error_code\":\"401\",\"name\":\"" + e.getClass() + "\",\"message\":\""
                                + e.getMessage() + "\"}");
                    }
                })
                // 使登录页不设限访问
                .permitAll()
                .and()
                .csrf().disable();
    }
}
