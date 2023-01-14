package com.sunny.maven.rpc.test.spi.service.impl;

import com.sunny.maven.rpc.spi.annotation.SPIClass;
import com.sunny.maven.rpc.test.spi.service.SpiService;

/**
 * @author SUNNY
 * @description: SPI服务实现类
 * @create: 2023-01-08 19:48
 */
@SPIClass
public class SpiServiceImpl implements SpiService {
    @Override
    public String hello(String name) {
        return "hello " + name;
    }
}
