package com.sunny.maven.user.security;

import com.sunny.maven.cache.template.CacheTemplate;
import com.sunny.maven.core.common.context.UserInfoContext;
import com.sunny.maven.core.common.context.UserInfoContextHolder;
import com.sunny.maven.core.common.resp.R;
import com.sunny.maven.core.utils.web.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author SUNNY
 * @description: 授权 filter
 * @create: 2022-09-14 17:19
 */
@Slf4j
public class JwtTokenAuthenticationFilter extends BasicAuthenticationFilter {
    private JwtRsaKeyProperties jwtRsaKeyProperties;
    private CacheTemplate cacheTemplate;
    public JwtTokenAuthenticationFilter(AuthenticationManager authenticationManager,
                                        JwtRsaKeyProperties jwtRsaKeyProperties, CacheTemplate cacheTemplate) {
        super(authenticationManager);
        this.jwtRsaKeyProperties = jwtRsaKeyProperties;
        this.cacheTemplate = cacheTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        log.info("==================={}", request.getRequestURI());
        if (request.getRequestURI().contains("/user/auth/")) {
            log.info("/user/auth/认证请求，不需要认证");
            chain.doFilter(request, response);
            return ;
        }
        String token = request.getHeader("token");
        if (StringUtils.hasText(token)) {
            if (JwtTokenUtil.isTokenExpired(token, jwtRsaKeyProperties.getPublicKey())) {
                ResponseUtils.out(response, R.error().message("认证已经失效!"));
                return ;
            }
            JwtTokenPayload claims = JwtTokenUtil.getInfoFromToken(token, jwtRsaKeyProperties.getPublicKey(),
                    JwtTokenUser.class);
            JwtTokenUser jwtTokenUser = (JwtTokenUser) claims.getUserInfo();
            String tokenId = jwtTokenUser.getToken();
            JwtTokenUser redisTokenUser = (JwtTokenUser) cacheTemplate.get(tokenId);
            Map<String, Object> userDto = new HashMap<>();
            userDto.put("username", redisTokenUser.getUsername());
            UserInfoContext userInfoContext = UserInfoContextHolder.createEmptyContext();
            userInfoContext.setTokenId(token);
            userInfoContext.setUserDto(userDto);
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    jwtTokenUser.getUsername(), userInfoContext, redisTokenUser.getAuthorities());
            UserInfoContextHolder.setAuthentication(authentication);
            chain.doFilter(request, response);
        } else {
            ResponseUtils.out(response, R.error().message("未提供认证token!"));
        }
    }
}