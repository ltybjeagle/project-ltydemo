package com.sunny.maven.security.provider;

import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author SUNNY
 * @description: 认证补充信息
 * @create: 2022-02-09 14:35
 */
@Component
public class Oauth2AuthenticationDetailsSource
        implements AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> {

    @Override
    public WebAuthenticationDetails buildDetails(HttpServletRequest request) {
        return new Oauth2WebAuthenticationDetails(request);
    }
}
