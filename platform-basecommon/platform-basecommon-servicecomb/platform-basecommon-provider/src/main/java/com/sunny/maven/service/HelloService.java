package com.sunny.maven.service;

import com.sunny.maven.exception.AppException;

/**
 * @author SUNNY
 * @description: 逻辑接口
 * @create: 2021-08-31 17:14
 */
public interface HelloService {
    /**
     * 请求接口
     * @param name 参数
     * @return 响应
     */
    String sayHello(String name) throws AppException;
}
