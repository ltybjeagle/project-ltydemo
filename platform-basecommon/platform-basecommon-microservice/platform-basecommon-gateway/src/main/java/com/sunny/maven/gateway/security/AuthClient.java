package com.sunny.maven.gateway.security;

import com.sunny.maven.gateway.security.fallback.AuthClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author SUNNY
 * @description: 认证鉴权
 * @create: 2022-09-23 11:23
 */
@FeignClient(value = "server-user", url = "http://localhost:8060", fallbackFactory = AuthClientFallback.class)
public interface AuthClient {

    /**
     * 认证函数
     * @param token
     * @return
     */
    @GetMapping(value = "/user/auth/token/userinfo")
    String verifyToken(@RequestParam("token") String token);
}
