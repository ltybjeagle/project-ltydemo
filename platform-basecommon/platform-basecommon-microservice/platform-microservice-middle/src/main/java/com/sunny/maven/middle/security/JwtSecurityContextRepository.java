package com.sunny.maven.middle.security;

import com.sunny.maven.core.common.constants.CommonConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.web.server.context.ServerSecurityContextRepository;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collection;

/**
 * @author SUNNY
 * @description: 上下文验证器
 * @create: 2022-11-03 13:44
 */
@Slf4j
public class JwtSecurityContextRepository implements ServerSecurityContextRepository {
    private JwtRsaKeyProperties jwtRsaKeyProperties;
    private JwtTokenByRedis jwtTokenByRedis;;

    @Override
    public Mono<Void> save(ServerWebExchange exchange, SecurityContext context) {
        return Mono.empty();
    }

    @Override
    public Mono<SecurityContext> load(ServerWebExchange exchange) {
        log.info("加载token:JwtSecurityContextRepository");
        String path = exchange.getRequest().getPath().toString();
        log.info("path={}", path);
        if (CommonConstant.LOGIN_AUTH_PATH.equals(path)) {
            return Mono.empty();
        }
        String tokenId = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (StringUtils.isNotBlank(tokenId)) {
            String token = jwtTokenByRedis.findObjectById(tokenId);
            if (token == null) {
                return Mono.empty();
            }
            if (JwtTokenUtil.isTokenExpired(token, jwtRsaKeyProperties.getPublicKey())) {
                return Mono.empty();
            }
            JwtTokenPayload claims = JwtTokenUtil.getInfoFromToken(token, jwtRsaKeyProperties.getPublicKey(),
                    JwtTokenUser.class);
            log.info("token 失效时间：{}", claims.getExpiration());
            JwtTokenUser jwtTokenUser = (JwtTokenUser) claims.getUserInfo();
            jwtTokenUser.setState(1);
            Authentication newAuthentication = new UsernamePasswordAuthenticationToken(jwtTokenUser.getUsername(),
                    jwtTokenUser);
            return ((ReactiveAuthenticationManager) authentication -> {
                Collection<? extends GrantedAuthority> authorities =
                        ((JwtTokenUser) authentication.getCredentials()).getAuthorities();
                Authentication auth = new UsernamePasswordAuthenticationToken(
                        authentication.getPrincipal().toString(), null, authorities);
                return Mono.just(auth);
            }).authenticate(newAuthentication).map(SecurityContextImpl::new);
        } else {
            return Mono.empty();
        }
    }

    public JwtSecurityContextRepository(JwtRsaKeyProperties jwtRsaKeyProperties, JwtTokenByRedis jwtTokenByRedis) {
        this.jwtRsaKeyProperties = jwtRsaKeyProperties;
        this.jwtTokenByRedis = jwtTokenByRedis;
    }
}
