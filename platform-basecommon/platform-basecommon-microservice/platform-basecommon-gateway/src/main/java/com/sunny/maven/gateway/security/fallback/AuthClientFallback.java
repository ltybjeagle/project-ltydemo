package com.sunny.maven.gateway.security.fallback;

import com.sunny.maven.gateway.security.AuthClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: 认证服务容错
 * @create: 2022-09-23 13:41
 */
@Slf4j
@Component
public class AuthClientFallback implements FallbackFactory<AuthClient> {

    @Override
    public AuthClient create(Throwable cause) {
        log.info("认证异常：{}", cause.getMessage());
        return auth -> cause.getMessage();
    }
}
