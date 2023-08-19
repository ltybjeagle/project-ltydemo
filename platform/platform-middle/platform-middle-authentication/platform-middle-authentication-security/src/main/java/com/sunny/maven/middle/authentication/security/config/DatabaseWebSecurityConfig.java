package com.sunny.maven.middle.authentication.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sunny.maven.middle.authentication.security.intercept.ExtendAccessDecisionManager;
import com.sunny.maven.middle.authentication.security.intercept.ExtendFilterInvocationSecurityMetadataSource;
import com.sunny.maven.middle.authentication.security.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author SUNNY
 * @description: 自定义Spring Security(基于数据库管理用户)
 * @create: 2023/8/11 18:27
 */
@Slf4j
@Configuration
@ComponentScan(basePackages = {"com.sunny.maven.middle.authentication.security.service"})
@ConditionalOnProperty(
        name = "sunny.security.authentication.type",
        havingValue = "db")
public class DatabaseWebSecurityConfig extends WebSecurityConfigurerAdapter {
    UserService userService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(10);
    }

    @Bean
    public RoleHierarchy roleHierarchy() {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String hierarchy = "ROLE_DBA > ROLE_ADMIN ROLE_ADMIN > ROLE_USER";
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public ExtendFilterInvocationSecurityMetadataSource cfisms() {
        return new ExtendFilterInvocationSecurityMetadataSource();
    }

    @Bean
    public ExtendAccessDecisionManager cadm() {
        return new ExtendAccessDecisionManager();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().
                withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
                    @Override
                    public <O extends FilterSecurityInterceptor> O postProcess(O object) {
                        object.setSecurityMetadataSource(cfisms());
                        object.setAccessDecisionManager(cadm());
                        return object;
                    }
                }).
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

    @Autowired
    public DatabaseWebSecurityConfig(UserService userService) {
        log.info("DatabaseWebSecurityConfig load..............");
        this.userService = userService;
    }
}
