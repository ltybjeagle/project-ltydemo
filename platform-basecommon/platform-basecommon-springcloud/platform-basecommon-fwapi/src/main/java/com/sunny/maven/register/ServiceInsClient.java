package com.sunny.maven.register;

/**
 * @author SUNNY
 * @description: 服务实例接口类
 * @create: 2021-12-09 17:47
 */
public interface ServiceInsClient {
    /**
     * 根据服务名查询服务实例信息
     * @param serviceName 服务名
     * @return Object
     */
    Object getServiceInsByName(String serviceName);
}
