package com.sunny.maven.usercenter.serviceins.controller;

import com.sunny.maven.register.ServiceInsClient;
import com.sunny.maven.usercenter.common.constant.UserCenterConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUNNY
 * @description: 服务实例REST接口类
 * @create: 2022-01-12 00:08
 */
@RestController("/usercenter")
public class ServiceInsController {
    private ServiceInsClient serviceInsClient;

    /**
     * 获取服务实例信息
     * @return Object
     */
    @GetMapping("/serviceinfo")
    public Object serviceUrl() {
        return serviceInsClient.getServiceInsByName(UserCenterConstant.APPLICATION_NAME);
    }

    /**
     * 构造函数
     * @param serviceInsClient serviceInsClient
     */
    @Autowired
    public ServiceInsController(ServiceInsClient serviceInsClient) {
        this.serviceInsClient = serviceInsClient;
    }
}
