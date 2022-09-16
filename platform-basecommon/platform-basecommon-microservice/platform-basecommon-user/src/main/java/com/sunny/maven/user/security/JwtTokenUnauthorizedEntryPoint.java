package com.sunny.maven.user.security;

import com.sunny.maven.core.common.resp.R;
import com.sunny.maven.core.utils.web.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author SUNNY
 * @description: 未授权统一处理handler
 * @create: 2022-09-14 16:41
 */
@Slf4j
public class JwtTokenUnauthorizedEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        ResponseUtils.out(response, R.error().message("认证失败!"));
    }
}
