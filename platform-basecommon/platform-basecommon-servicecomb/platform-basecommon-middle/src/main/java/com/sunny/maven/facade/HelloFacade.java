package com.sunny.maven.facade;

/**
 * @author SUNNY
 * @description: 服务接口
 * @create: 2021-08-31 17:06
 */
public interface HelloFacade {

    /**
     * 请求接口
     * @param name 参数
     * @return 响应
     * @throws Exception 异常
     */
    String sayHello(String name) throws Exception;
}
