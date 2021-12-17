package com.sunny.maven.controller;

import com.sunny.maven.facade.api.UserRemoteClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author SUNNY
 * @description: 登录Rest接口
 * @create: 2021-11-26 15:17
 */
@RestController
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    private RestTemplate restTemplate;
    private static final String USER_CENTER = "usercenter-service";
    private UserRemoteClient userRemoteClient;

    @GetMapping("/portal/login")
    public String callHello() {
        StopWatch stopWatch = new StopWatch("/portal/login");
        stopWatch.start();
        String url = String.format("http://%s/user/hello", USER_CENTER);
        logger.info("restTemplate：{}", restTemplate.getForObject(url, String.class));
        String result = userRemoteClient.hello();
        logger.info("Feign client：{}", result);
        stopWatch.stop();
        logger.info("result：{}", stopWatch.getTotalTimeMillis());
        return result;
    }

    /**
     * 构造函数
     * @param restTemplate REST模板
     */
    @Autowired
    public LoginController(RestTemplate restTemplate, UserRemoteClient userRemoteClient) {
        this.restTemplate = restTemplate;
        this.userRemoteClient = userRemoteClient;
    }
}
