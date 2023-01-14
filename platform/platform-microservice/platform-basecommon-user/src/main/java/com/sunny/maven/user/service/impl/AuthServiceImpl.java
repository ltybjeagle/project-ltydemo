package com.sunny.maven.user.service.impl;

import com.sunny.maven.cache.template.CacheTemplate;
import com.sunny.maven.core.common.constants.CommonConstant;
import com.sunny.maven.core.common.context.UserInfoContext;
import com.sunny.maven.core.utils.crypto.Base64Utils;
import com.sunny.maven.user.security.JwtRsaKeyProperties;
import com.sunny.maven.user.security.JwtTokenPayload;
import com.sunny.maven.user.security.JwtTokenUser;
import com.sunny.maven.user.security.JwtTokenUtil;
import com.sunny.maven.user.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * @author SUNNY
 * @description: 认证鉴权
 * @create: 2022-09-23 14:07
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {
    private JwtRsaKeyProperties jwtRsaKeyProperties;
    private CacheTemplate cacheTemplate;
    private Jackson2JsonRedisSerializer jackson2JsonRedisSerializer;

    /**
     * 认证函数
     * @param tokenId
     * @return
     */
    @Override
    public String verifyToken(String tokenId) {
        UserInfoContext userInfoContext = (UserInfoContext) cacheTemplate.get(tokenId);
        if (userInfoContext == null) {
            return null;
        }
        String token = userInfoContext.getTokenId();
        if (JwtTokenUtil.isTokenExpired(token, jwtRsaKeyProperties.getPublicKey())) {
            return null;
        }
        JwtTokenPayload claims = JwtTokenUtil.getInfoFromToken(token, jwtRsaKeyProperties.getPublicKey(),
                JwtTokenUser.class);
        log.info("token 失效时间：{}", claims.getExpiration());
        JwtTokenUser jwtTokenUser = (JwtTokenUser) claims.getUserInfo();
        jwtTokenUser.setState(1);
        String refreshedToken = JwtTokenUtil.generateTokenExpireInMinutes(jwtTokenUser,
                jwtRsaKeyProperties.getPrivateKey(), CommonConstant.EXPIRATION_TIME);
        userInfoContext.setTokenId(refreshedToken);
        cacheTemplate.put(tokenId, userInfoContext, CommonConstant.SESSION_REDIS_TIME, TimeUnit.MINUTES);
        String userInfo = new String(jackson2JsonRedisSerializer.serialize(userInfoContext));
        try {
            userInfo = Base64Utils.encryptBase64(userInfo.getBytes());
        } catch (Exception ignored) {
            userInfo = null;
        }
        return userInfo;
    }

    /**
     * 构造函数
     * @param jwtRsaKeyProperties
     * @param cacheTemplate
     */
    @Autowired
    public AuthServiceImpl(JwtRsaKeyProperties jwtRsaKeyProperties, CacheTemplate cacheTemplate,
                           Jackson2JsonRedisSerializer jackson2JsonRedisSerializer) {
        this.jwtRsaKeyProperties = jwtRsaKeyProperties;
        this.cacheTemplate = cacheTemplate;
        this.jackson2JsonRedisSerializer = jackson2JsonRedisSerializer;
    }
}
