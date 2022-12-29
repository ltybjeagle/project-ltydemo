package com.sunny.maven.core.fluxclient.interfaces;

/**
 * @author SUNNY
 * @description: 创建代理类接口
 * @create: 2022-09-30 10:20
 */
public interface ProxyCreator {
    /**
     * 创建代理类
     * @param type
     * @return
     */
    Object createProxy(Class<?> type);
}
