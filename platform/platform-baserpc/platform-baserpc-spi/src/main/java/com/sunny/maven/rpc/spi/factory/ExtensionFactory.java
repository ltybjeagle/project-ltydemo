package com.sunny.maven.rpc.spi.factory;

import com.sunny.maven.rpc.spi.annotation.SPI;

/**
 * @author SUNNY
 * @description: ExtensionFactory
 * @create: 2023-01-06 11:12
 */
@SPI("spi")
public interface ExtensionFactory {

    /**
     * 获取扩展类对象
     * @param key 传入的key值
     * @param clazz Class类型对象
     * @param <T> 泛型类型
     * @return 扩展类对象
     */
    <T> T getExtensionFactory(String key, Class<T> clazz);
}
