package com.sunny.maven.middle.authentication.oauth2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * @author SUNNY
 * @description:
 * @create: 2023/8/14 15:40
 */
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return super.userDetailsService();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().
                withUser("admin").
                password("$2a$10$UhHxgsyD6eIwFkHEEBxXRuuxMppJOmcukEcSdbYU30p8TgnHuXI42").
                roles("ADMIN").
                and().
                withUser("sunny").
                password("$2a$10$UhHxgsyD6eIwFkHEEBxXRuuxMppJOmcukEcSdbYU30p8TgnHuXI42").
                roles("USER");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.antMatcher("/oauth/**").authorizeRequests().
                antMatchers("/oauth/**").permitAll().
                and().
                csrf().
                disable();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().
                antMatchers("/v2/api-docs", "/swagger-resources/configuration/ui",
                        "/swagger-resources","/swagger-resources/configuration/security",
                        "/swagger-ui.html","/webjars/**")
                .antMatchers("/**/*.html", "/**/*.js", "/favicon.ico");
    }
}
