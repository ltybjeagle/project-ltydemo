package com.sunny.maven.rpc.spi.factory;

import com.sunny.maven.rpc.spi.annotation.SPI;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import com.sunny.maven.rpc.spi.loader.ExtensionLoader;

import java.util.Optional;

/**
 * @author SUNNY
 * @description: SpiExtensionFactory
 * @create: 2023-01-06 11:17
 */
@SPIClass
public class SpiExtensionFactory implements ExtensionFactory {
    @Override
    public <T> T getExtensionFactory(String key, Class<T> clazz) {
//        return Optional.ofNullable(clazz).
//                filter(Class::isInterface).
//                filter(cls -> cls.isAnnotationPresent(SPI.class)).
//                map(ExtensionLoader::getExtensionLoader).
//                map();
        return null;
    }
}
