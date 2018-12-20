package com.liuty.maven.controller;

import com.liuty.maven.dubbofacade.UserFacade;
import com.liuty.maven.entity.UserEntity;
import com.liuty.maven.springutil.SpringContextUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebUserController {

    @GetMapping("/dubbo-user/{id}")
    public UserEntity findById(@PathVariable String id) {
        UserFacade userFacade = SpringContextUtil.getBean("userFacade");
        UserEntity userEntity = null;
        try {
            userEntity = userFacade.findUserById(id);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return userEntity;
    }
}
