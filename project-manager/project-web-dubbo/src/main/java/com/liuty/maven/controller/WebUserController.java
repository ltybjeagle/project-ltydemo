package com.liuty.maven.controller;

import com.liuty.maven.dubbofacade.UserFacade;
import com.liuty.maven.entity.UserEntity;
import com.liuty.maven.springutil.SpringContextUtil;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WebUserController {

    @ApiOperation(value = "获取用户详细信息", notes = "根据url的id来获取用户详细信息")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, dataType = "String", paramType = "path")
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
