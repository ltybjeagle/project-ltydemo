package com.sunny.maven.microservice.authentication.jwt.utils;

import com.sunny.maven.microservice.authentication.jwt.userdetails.JwtUserDetails;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author SUNNY
 * @description: 生成/验证令牌工具类
 * @create: 2023-04-02 11:27
 */
@Getter
public class JwtTokenUtils {
    private String secret = "abcdefghijklmn";
    private Long expiration = 7200000L;
    private String header = "token";

    private static volatile JwtTokenUtils instance;

    private String generateToken(Map<String, Object> claims) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
        return Jwts.builder().
                setClaims(claims).
                setExpiration(expirationDate).
                signWith(SignatureAlgorithm.RS256, secret).
                compact();
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        } catch (Exception e) {
            claims = null;
        }
        return claims;
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>(2);
        claims.put("sub", userDetails.getUsername());
        claims.put("created", new Date());
        return generateToken(claims);
    }

    public String getUsernameFromToken(String token) {
        String username;
        try {
            Claims claims = getClaimsFromToken(token);
            username = claims.getSubject();
        } catch (Exception e) {
            username = null;
        }
        return username;
    }

    public Boolean isTokenExpired(String token) {
        try {
            Claims claims = getClaimsFromToken(token);
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    public String refreshToken(String token) {
        String refreshToken;
        try {
            Claims claims = getClaimsFromToken(token);
            claims.put("created", new Date());
            refreshToken = generateToken(claims);
        } catch (Exception e) {
            refreshToken = null;
        }
        return refreshToken;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        JwtUserDetails jwtTokenUser = (JwtUserDetails) userDetails;
        String username = getUsernameFromToken(token);
        return (username.equals(jwtTokenUser.getUsername())) && !isTokenExpired(token);
    }

    public static JwtTokenUtils getInstance() {
        if (instance == null) {
            synchronized (JwtTokenUtils.class) {
                if (instance == null) {
                    instance = new JwtTokenUtils();
                }
            }
        }
        return instance;
    }

    private JwtTokenUtils() {}
}
