package com.sunny.maven.facade.api;

import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author SUNNY
 * @description: 用户中心接口
 * @create: 2021-12-15 14:56
 */
public interface UserRemoteClient {

    /**
     * 用户中心客户端接口
     * @return String
     */
    @GetMapping("/user/hello")
    String hello();
}
