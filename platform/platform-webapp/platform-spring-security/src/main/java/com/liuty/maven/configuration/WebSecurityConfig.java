package com.liuty.maven.configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @description: 认证
 * @author: Sunny
 * @date: 2019/12/28
 */
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 资源授权
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/admin/api/**").hasRole("ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers("/app/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin();
    }

    /**
     * 基于内存用户
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        String pwd = (new BCryptPasswordEncoder()).encode("123");
        auth.inMemoryAuthentication()
                .passwordEncoder(new BCryptPasswordEncoder())
                .withUser(User.withUsername("user").password(pwd).roles("USER").build())
                .withUser(User.withUsername("admin").password(pwd).roles("USER, ADMIN").build());
    }

    /**
     * 表单认证
     * @param http
     * @throws Exception
     */
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .anyRequest()
//                .authenticated()
//                .and()
//            .formLogin()
//                .loginPage("/myLogin.html")
//                // 指定处理登陆请求的路径
//                .loginProcessingUrl("/login")
//                // 指定登陆成功时的处理逻辑
//                .successHandler(new AuthenticationSuccessHandler() {
//            @Override
//            public void onAuthenticationSuccess(HttpServletRequest httpServletRequest
//                    , HttpServletResponse httpServletResponse
//                    , Authentication authentication) throws IOException, ServletException {
//                httpServletResponse.setContentType("application/json;charset=UTF-8");
//                PrintWriter out = httpServletResponse.getWriter();
//                out.write("{\"error_code\":\"0\",\"message\":\"欢迎登陆系统\"}");
//            }
//        })
//                // 指定登陆失败时的处理逻辑
//                .failureHandler(new AuthenticationFailureHandler() {
//            @Override
//            public void onAuthenticationFailure(HttpServletRequest httpServletRequest
//                    , HttpServletResponse httpServletResponse
//                    , AuthenticationException e) throws IOException, ServletException {
//                httpServletResponse.setContentType("application/json;charset=UTF-8");
//                httpServletResponse.setStatus(401);
//                PrintWriter out = httpServletResponse.getWriter();
//                out.write("{\"error_code\":\"401\",\"name\":\"" + e.getClass() + "\",\"message\":\""
//                        + e.getMessage() + "\"}");
//            }
//        })
//                // 使登录页不设限访问
//                .permitAll()
//                .and()
//            .csrf().disable();
//    }
}
