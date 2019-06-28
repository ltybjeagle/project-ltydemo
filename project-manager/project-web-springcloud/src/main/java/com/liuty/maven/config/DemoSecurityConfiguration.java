package com.liuty.maven.config;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * @描述: 认证与授权配置类(通过扩展配置)
 *
 * @创建人: Sunny
 * @创建时间: 2019/6/28
 */
@Configuration
@Order(SecurityProperties.DEFAULT_FILTER_ORDER)
public class DemoSecurityConfiguration extends WebSecurityConfigurerAdapter {
    private DemoSecurityConfiguration() {
        super(true);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        // 定义需要忽略的url等操作
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 配置url安全拦截配置
    }
}
