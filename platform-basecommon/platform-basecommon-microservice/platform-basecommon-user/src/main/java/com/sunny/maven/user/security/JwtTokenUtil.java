package com.sunny.maven.user.security;

import com.sunny.maven.core.utils.json.JsonUtils;
import io.jsonwebtoken.*;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * @author SUNNY
 * @description: 生成/验证令牌工具类
 * @create: 2022-09-07 15:59
 */
public class JwtTokenUtil {
    private static final String JWT_PAYLOAD_USER_KEY = "user";

    /**
     * 私钥加密token
     * @param userInfo 载荷中的数据
     * @param privateKey 私钥
     * @param expire 过期时间，单位分钟
     * @return JWT
     */
    public static String generateTokenExpireInMinutes(Object userInfo, PrivateKey privateKey, int expire) {
        return Jwts.builder().
                claim(JWT_PAYLOAD_USER_KEY, JsonUtils.toJson(userInfo)).
                setId(createJIT()).
                setExpiration(
                        Date.from(LocalDateTime.now().plusMinutes(expire).atZone(ZoneId.systemDefault()).toInstant())).
                signWith(SignatureAlgorithm.RS256, privateKey).
                compact();
    }

    /**
     * 私钥加密token
     * @param userInfo 载荷中的数据
     * @param privateKey 私钥
     * @param expire 过期时间，单位秒
     * @return JWT
     */
    public static String generateTokenExpireInSeconds(Object userInfo, PrivateKey privateKey, int expire) {
        return Jwts.builder().
                claim(JWT_PAYLOAD_USER_KEY, JsonUtils.toJson(userInfo)).
                setId(createJIT()).
                setExpiration(
                        Date.from(LocalDateTime.now().plusSeconds(expire).atZone(ZoneId.systemDefault()).toInstant())).
                signWith(SignatureAlgorithm.RS256, privateKey).
                compact();
    }

    /**
     * 公钥解析token
     * @param token 用户请求中的token
     * @param publicKey 公钥
     * @return Jws<Claims>
     */
    private static Jws<Claims> parserToken(String token, PublicKey publicKey) {
        return Jwts.parser().setSigningKey(publicKey).parseClaimsJws(token);
    }

    private static String createJIT() {
        return new String(Base64.getEncoder().encode(UUID.randomUUID().toString().getBytes()));
    }

    /**
     * 校验token是否失效
     * @param token
     * @param token
     * @return
     */
    public static Boolean isTokenExpired(String token, PublicKey publicKey) {
        Jws<Claims> claimsJws = null;
        try {
            claimsJws =  parserToken(token, publicKey);
        } catch (ExpiredJwtException ignored) {
        }
        return Optional.ofNullable(claimsJws).isPresent();
    }

    /**
     * 获取token中的用户信息
     * @param token 用户请求中的令牌
     * @param publicKey 公钥
     * @param userType
     * @param <T>
     * @return Payload<T>
     */
    public static <T> JwtTokenPayload<T> getInfoFromToken(String token, PublicKey publicKey, Class<T> userType) {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        JwtTokenPayload<T> claims = new JwtTokenPayload<>();
        claims.setId(body.getId());
        claims.setUserInfo(JsonUtils.parse(body.get(JWT_PAYLOAD_USER_KEY).toString(), userType));
        claims.setExpiration(body.getExpiration());
        return claims;
    }

    /**
     * 获取token中的载荷信息
     * @param token 用户请求中的令牌
     * @param publicKey 公钥
     * @param <T>
     * @return Payload<T>
     */
    public static <T> JwtTokenPayload<T> getInfoFromToken(String token, PublicKey publicKey) {
        Jws<Claims> claimsJws = parserToken(token, publicKey);
        Claims body = claimsJws.getBody();
        JwtTokenPayload<T> claims = new JwtTokenPayload<>();
        claims.setId(body.getId());
        claims.setExpiration(body.getExpiration());
        return claims;
    }
}
