package com.sunny.maven.rpc.registry.api.config;

import java.io.Serializable;

/**
 * @author SUNNY
 * @description: 注册配置类
 * @create: 2023-01-03 17:54
 */
public class RegistryConfig implements Serializable {
    private static final long serialVersionUID = -2352240685663362337L;
    /**
     * 注册地址
     */
    private String registryAddr;
    /**
     * 注册类型
     */
    private String registryType;

    public RegistryConfig(String registryAddr, String registryType) {
        this.registryAddr = registryAddr;
        this.registryType = registryType;
    }

    public String getRegistryAddr() {
        return registryAddr;
    }

    public void setRegistryAddr(String registryAddr) {
        this.registryAddr = registryAddr;
    }

    public String getRegistryType() {
        return registryType;
    }

    public void setRegistryType(String registryType) {
        this.registryType = registryType;
    }
}
