package com.sunny.maven.middle.security;

import com.sunny.maven.core.common.resp.R;
import com.sunny.maven.core.utils.ResponseUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AccountExpiredException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationFailureHandler;
import reactor.core.publisher.Mono;

/**
 * @author SUNNY
 * @description: 验证失败处理器
 * @create: 2022-11-03 14:21
 */
public class JwtServerAuthenticationFailureHandler implements ServerAuthenticationFailureHandler {
    @Override
    public Mono<Void> onAuthenticationFailure(WebFilterExchange webFilterExchange, AuthenticationException exception) {
        webFilterExchange.getExchange().getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        String rstMsg;
        if (exception instanceof UsernameNotFoundException) {
            rstMsg = "用户不存在 " + exception.getMessage();
        } else if (exception instanceof BadCredentialsException) {
            rstMsg = "密码错误 " + exception.getMessage();
        } else if (exception instanceof LockedException) {
            rstMsg = "用户锁定 " + exception.getMessage();
        } else if (exception instanceof AccountExpiredException) {
            rstMsg = "用户过期 " + exception.getMessage();
        } else if (exception instanceof DisabledException) {
            rstMsg = "用户不可用 " + exception.getMessage();
        } else {
            rstMsg = "系统错误 " + exception.getMessage();
        }
        return ResponseUtils.writeWith(webFilterExchange.getExchange(),
                R.error().code(HttpStatus.UNAUTHORIZED.value()).message(rstMsg));
    }
}
