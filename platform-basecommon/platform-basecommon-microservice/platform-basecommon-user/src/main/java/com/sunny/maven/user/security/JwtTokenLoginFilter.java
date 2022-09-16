package com.sunny.maven.user.security;

import com.sunny.maven.core.common.resp.R;
import com.sunny.maven.core.utils.web.ResponseUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * @author SUNNY
 * @description: 认证过滤器
 * @create: 2022-09-13 16:23
 */
public class JwtTokenLoginFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;
    private JwtRsaKeyProperties jwtRsaKeyProperties;

    public JwtTokenLoginFilter(AuthenticationManager authenticationManager, JwtRsaKeyProperties jwtRsaKeyProperties) {
        this.authenticationManager = authenticationManager;
        this.jwtRsaKeyProperties = jwtRsaKeyProperties;
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
        String token = JwtTokenUtil.generateTokenExpireInMinutes(jwtTokenUser, jwtRsaKeyProperties.getPrivateKey(),
                30);
        ResponseUtils.out(response, R.ok().data(token));
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
