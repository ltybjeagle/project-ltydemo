package com.sunny.maven.middle.security;

import com.sunny.maven.core.common.constants.CommonConstant;
import com.sunny.maven.core.common.resp.R;
import com.sunny.maven.core.utils.ResponseUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * @author SUNNY
 * @description: 验证成功处理器
 * @create: 2022-11-03 15:02
 */
public class JwtServerAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {
    private JwtRsaKeyProperties jwtRsaKeyProperties;
    private JwtTokenByRedis jwtTokenByRedis;

    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        JwtTokenUser jwtTokenUser = (JwtTokenUser) authentication.getPrincipal();
        String tokenId = jwtTokenUser.getToken();
        String token = JwtTokenUtil.generateTokenExpireInMinutes(jwtTokenUser, jwtRsaKeyProperties.getPrivateKey(),
                CommonConstant.EXPIRATION_TIME);
        if (!jwtTokenByRedis.saveObject(tokenId, token, Duration.ofMinutes(CommonConstant.SESSION_REDIS_TIME))) {
            throw new RuntimeException("用户会话存储异常");
        }
        return ResponseUtils.writeWith(webFilterExchange.getExchange(), R.ok().data(tokenId));
    }

    /**
     * 构造函数
     * @param jwtRsaKeyProperties
     */
    public JwtServerAuthenticationSuccessHandler(JwtRsaKeyProperties jwtRsaKeyProperties,
                                                 JwtTokenByRedis jwtTokenByRedis) {
        this.jwtRsaKeyProperties = jwtRsaKeyProperties;
        this.jwtTokenByRedis = jwtTokenByRedis;
    }
}
