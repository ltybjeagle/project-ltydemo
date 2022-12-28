package com.sunny.maven.user.security;

import com.sunny.maven.cache.template.CacheTemplate;
import com.sunny.maven.core.common.constants.CommonConstant;
import com.sunny.maven.core.common.context.UserInfoContext;
import com.sunny.maven.core.common.context.UserInfoContextHolder;
import com.sunny.maven.core.common.domain.UserDto;
import com.sunny.maven.core.common.resp.R;
import com.sunny.maven.core.utils.web.ResponseUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @author SUNNY
 * @description: 认证过滤器
 * @create: 2022-09-13 16:23
 */
public class JwtTokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private JwtRsaKeyProperties jwtRsaKeyProperties;
    private CacheTemplate cacheTemplate;

    public JwtTokenLoginFilter(AuthenticationManager authenticationManager, JwtRsaKeyProperties jwtRsaKeyProperties,
                               CacheTemplate cacheTemplate) {
        this.authenticationManager = authenticationManager;
        this.jwtRsaKeyProperties = jwtRsaKeyProperties;
        this.cacheTemplate = cacheTemplate;
        this.setPostOnly(false);
        this.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher("/auth/login", "POST"));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            Map<String, String[]> map = request.getParameterMap();
            String username = map.get("username")[0];
            String password = map.get("password")[0];
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 登录成功
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                            FilterChain chain, Authentication authResult)
            throws IOException, ServletException {
        JwtTokenUser jwtTokenUser = (JwtTokenUser) authResult.getPrincipal();
        String tokenId = jwtTokenUser.getToken();
        UserDto userDto = UserDto.createUserDto();
        userDto.setUsername(jwtTokenUser.getUsername());
        userDto.setAuthorities(
                jwtTokenUser.getAuthorities().
                        stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList()));
        UserInfoContext userInfoContext = UserInfoContextHolder.createEmptyContext();
        userInfoContext.setUserDto(userDto);
        String token = JwtTokenUtil.generateTokenExpireInMinutes(jwtTokenUser, jwtRsaKeyProperties.getPrivateKey(),
                CommonConstant.EXPIRATION_TIME);
        userInfoContext.setTokenId(token);
        cacheTemplate.put(tokenId, userInfoContext, CommonConstant.SESSION_REDIS_TIME, TimeUnit.MINUTES);
        ResponseUtils.out(response, R.ok().data(tokenId));
    }

    /**
     * 登录失败
     * @param request
     * @param response
     * @param failed
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        ResponseUtils.out(response, R.error().message("登录失败"));
    }
}
