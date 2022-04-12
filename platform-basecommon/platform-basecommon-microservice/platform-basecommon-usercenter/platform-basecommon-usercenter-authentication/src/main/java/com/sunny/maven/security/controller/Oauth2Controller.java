package com.sunny.maven.security.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author SUNNY
 * @description: 认证控制器
 * @create: 2022-02-08 17:42
 */
@RestController
@RequestMapping(value = "/authentication")
public class Oauth2Controller {
    /**
     * 验证码
     */
    private Producer captchaProducer;

    /**
     * 登录前
     * @param request 请求
     * @param response 响应
     * @return String
     */
    @GetMapping(value = "/authProLogin")
    public String authProLogin(HttpServletRequest request, HttpServletResponse response) {
        String capText = captchaProducer.createText();
        // 将验证码设置到session
        request.getSession().setAttribute("captcha", capText);
        return capText;
    }

    /**
     * 登录认证
     * @param request 请求
     * @param response 响应
     * @return String
     */
    @GetMapping(value = "/authLogin")
    public String authLogin(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("authLogin");
        return null;
    }

    /**
     * 构造注入
     * @param captchaProducer 验证码
     */
    @Autowired
    public Oauth2Controller(Producer captchaProducer) {
        this.captchaProducer = captchaProducer;
    }
}
