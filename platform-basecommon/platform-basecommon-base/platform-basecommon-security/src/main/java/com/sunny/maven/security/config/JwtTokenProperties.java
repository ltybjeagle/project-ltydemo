package com.sunny.maven.security.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: 认证服务配置
 * @create: 2022-02-23 12:28
 */
@Configuration
@ConfigurationProperties(prefix = "sunny.security")
public class JwtTokenProperties {
    /**
     * 使用jwt时为token签名的秘钥
     */
    private String jwtSigningKey = "sunny&202202221235";
    /**
     * 客户端ID
     */
    private String clientId = "platform-gateway";
    /**
     * 客户端secret值
     */
    private String clientSecret = "$2a$10$wYrurs4tcwKl6NUKIu7N9OyHkvM0WZv/Ze.R3PITMLsztmtvFvd6u";
    /**
     * 客户端用户信息，没有此信息无法调用任务接口
     */
    private String clientUser = "cGFhc2hzcC1nYXRld2F5OlBBQVNIc3AmR2F0ZXdheQ==";
    /**
     * token失效时间(单位：s)
     */
    private int accessTokenValidateSeconds = 30 * 60;
    /**
     * token认证信息失效时间
     */
    private long tokenInfoTimeOut = 1L;
    /**
     * token认证信息失效时间(单位)
     */
    private TimeUnit tokenInfoTimeUnit = TimeUnit.HOURS;
    /**
     * token失效时间(redis缓存时间)
     */
    private long tokenRedisTimeOut = 25L;
    /**
     * token失效时间(redis缓存时间，单位)
     */
    private TimeUnit tokenRedisTimeUnit = TimeUnit.MINUTES;

    public String getJwtSigningKey() {
        return jwtSigningKey;
    }

    public void setJwtSigningKey(String jwtSigningKey) {
        this.jwtSigningKey = jwtSigningKey;
    }

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

    public String getClientUser() {
        return clientUser;
    }

    public void setClientUser(String clientUser) {
        this.clientUser = clientUser;
    }

    public int getAccessTokenValidateSeconds() {
        return accessTokenValidateSeconds;
    }

    public void setAccessTokenValidateSeconds(int accessTokenValidateSeconds) {
        this.accessTokenValidateSeconds = accessTokenValidateSeconds;
    }

    public long getTokenInfoTimeOut() {
        return tokenInfoTimeOut;
    }

    public void setTokenInfoTimeOut(long tokenInfoTimeOut) {
        this.tokenInfoTimeOut = tokenInfoTimeOut;
    }

    public TimeUnit getTokenInfoTimeUnit() {
        return tokenInfoTimeUnit;
    }

    public void setTokenInfoTimeUnit(TimeUnit tokenInfoTimeUnit) {
        this.tokenInfoTimeUnit = tokenInfoTimeUnit;
    }

    public long getTokenRedisTimeOut() {
        return tokenRedisTimeOut;
    }

    public void setTokenRedisTimeOut(long tokenRedisTimeOut) {
        this.tokenRedisTimeOut = tokenRedisTimeOut;
    }

    public TimeUnit getTokenRedisTimeUnit() {
        return tokenRedisTimeUnit;
    }

    public void setTokenRedisTimeUnit(TimeUnit tokenRedisTimeUnit) {
        this.tokenRedisTimeUnit = tokenRedisTimeUnit;
    }
}
