package com.sunny.maven.middle.security;

import com.sunny.maven.core.common.constants.CommonConstant;
import com.sunny.maven.core.common.resp.R;
import com.sunny.maven.core.utils.ResponseUtils;
import com.sunny.maven.middle.redis.repository.ReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * @author SUNNY
 * @description: EnableWebFluxSecurity权限验证配置
 * @create: 2022-11-03 13:50
 */
@Slf4j
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class JwtWebFluxSecurityConfigurer {
    /**
     * 认证密钥
     */
    private JwtRsaKeyProperties jwtRsaKeyProperties;
    /**
     * 认证缓存对象
     */
    private ReactiveRepository reactiveValueOperationsRepositoryImpl;
    /**
     * 密码管理工具
     */
    private PasswordEncoder passwordEncoder;

    @Bean
    public JwtTokenByRedis jwtTokenByRedis() {
        return new JwtTokenByRedis(CommonConstant.Redis.TOKEN_CACHE_KEY, reactiveValueOperationsRepositoryImpl);
    }

    @Bean
    public ReactiveAuthenticationManager authenticationManager(ReactiveUserDetailsService userDetailsService) {
        log.info("加载Security用户配置......");
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager =
                new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }

    @Bean
    public SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http, JwtTokenByRedis jwtTokenByRedis) {
        log.info("加载Security权限配置......");
        http.
                csrf().disable().
                cors().disable().
                httpBasic().disable().
                securityContextRepository(new JwtSecurityContextRepository(jwtRsaKeyProperties, jwtTokenByRedis)).
                formLogin().
                authenticationFailureHandler(new JwtServerAuthenticationFailureHandler()).
                loginPage(CommonConstant.LOGIN_AUTH_PATH).
                authenticationSuccessHandler(new JwtServerAuthenticationSuccessHandler(jwtRsaKeyProperties,
                        jwtTokenByRedis)).
                and().
                // 请求进行授权
                authorizeExchange().
                // 特殊请求过滤
                pathMatchers(HttpMethod.OPTIONS).permitAll().
                // 登录不需要验证
                pathMatchers(CommonConstant.LOGIN_AUTH_PATH).permitAll().
                // 任何请求，都需要认证
                anyExchange().authenticated().
                and().logout().logoutSuccessHandler((webFilterExchange, authentication) ->
                ResponseUtils.writeWith(webFilterExchange.getExchange(), R.ok().message("退出成功"))).
                and().exceptionHandling().accessDeniedHandler((exchange, denied) ->
                ResponseUtils.writeWith(exchange, R.error().message("账户无权限访问")))
        ;
        return http.build();
    }

    @Autowired
    public JwtWebFluxSecurityConfigurer(JwtRsaKeyProperties jwtRsaKeyProperties,
                                        ReactiveRepository reactiveValueOperationsRepositoryImpl,
                                        PasswordEncoder passwordEncoder) {
        this.jwtRsaKeyProperties = jwtRsaKeyProperties;
        this.reactiveValueOperationsRepositoryImpl = reactiveValueOperationsRepositoryImpl;
        this.passwordEncoder = passwordEncoder;
    }
}
