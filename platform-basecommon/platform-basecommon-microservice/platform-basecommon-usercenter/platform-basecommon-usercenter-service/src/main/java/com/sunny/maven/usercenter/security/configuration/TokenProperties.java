package com.sunny.maven.usercenter.security.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author SUNNY
 * @description: Token信息配置
 * @create: 2022-03-11 16:39
 */
@Configuration
@ConfigurationProperties(prefix = "sunny.security")
public class TokenProperties {
    /**
     * 客户端ID
     */
    private String clientId = "platform-gateway";
    /**
     * 客户端secret值
     */
    private String clientSecret = "$2a$10$wYrurs4tcwKl6NUKIu7N9OyHkvM0WZv/Ze.R3PITMLsztmtvFvd6u";
    /**
     * token失效时间(单位：s)
     */
    private int accessTokenValidateSeconds = 30 * 60;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public int getAccessTokenValidateSeconds() {
        return accessTokenValidateSeconds;
    }

    public void setAccessTokenValidateSeconds(int accessTokenValidateSeconds) {
        this.accessTokenValidateSeconds = accessTokenValidateSeconds;
    }
}
