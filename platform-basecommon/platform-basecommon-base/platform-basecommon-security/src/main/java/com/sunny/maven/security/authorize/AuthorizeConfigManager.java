package com.sunny.maven.security.authorize;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;

/**
 * @author SUNNY
 * @description: 授权信息管理器，用于收集系统中所有 AuthorizeConfigProvider 并加载其配置
 * @create: 2022-02-23 14:28
 */
public interface AuthorizeConfigManager {
    /**
     * Config.
     * @param config the config
     */
    void config(ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry config);
}
