package com.sunny.maven.middle.user.controller;

import com.sunny.maven.core.common.constants.CommonConstant;
import com.sunny.maven.core.common.resp.R;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * @author SUNNY
 * @description: 认证鉴权
 * @create: 2022-11-04 16:32
 */
@RestController
public class AuthController {

    @RequestMapping(value = CommonConstant.LOGIN_AUTH_PATH, method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Mono<Object> login(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return Mono.just(R.error().message("您还未登录"));
    }
}
