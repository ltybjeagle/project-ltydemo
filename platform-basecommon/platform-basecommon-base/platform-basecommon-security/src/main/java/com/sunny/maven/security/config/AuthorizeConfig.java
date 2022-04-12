package com.sunny.maven.security.config;

import com.sunny.maven.security.authorize.AuthorizeConfigProvider;
import com.sunny.maven.security.authorize.SunnyAuthorizeConfigManager;
import com.sunny.maven.security.authorize.SunnyAuthorizeConfigProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author SUNNY
 * @description: 授权配置类
 * @create: 2022-02-23 14:44
 */
@Configuration
public class AuthorizeConfig {

    private List<AuthorizeConfigProvider> authorizeConfigProviders;

    @Bean
    public SunnyAuthorizeConfigManager sunnyAuthorizeConfigManager() {
        return new SunnyAuthorizeConfigManager(authorizeConfigProviders);
    }

    @Bean
    public SunnyAuthorizeConfigProvider sunnyAuthorizeConfigProvider() {
        return new SunnyAuthorizeConfigProvider();
    }

    @Autowired
    public AuthorizeConfig(List<AuthorizeConfigProvider> authorizeConfigProviders) {
        this.authorizeConfigProviders = authorizeConfigProviders;
    }

}
