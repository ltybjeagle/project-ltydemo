package com.sunny.maven.rpc.test.spi.service;

import com.sunny.maven.rpc.spi.annotation.SPI;

/**
 * @author SUNNY
 * @description: SPIService
 * @create: 2023-01-08 19:46
 */
@SPI(value = "spiService")
public interface SpiService {
    String hello(String name);
}
