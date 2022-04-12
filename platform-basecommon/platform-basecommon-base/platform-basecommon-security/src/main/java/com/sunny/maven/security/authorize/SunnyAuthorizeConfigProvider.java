package com.sunny.maven.security.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author SUNNY
 * @description: 核心模块的授权配置提供器，安全模块涉及的url的授权配置在这里
 * @create: 2022-02-23 14:43
 */
public class SunnyAuthorizeConfigProvider implements AuthorizeConfigProvider {
    @Override
    public boolean config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config) {
        config.antMatchers(
                "/druid/**",
                "/oauth/**",
                "/swagger-ui.html",
                "/swagger-resources/**").permitAll();
        return false;
    }
}
