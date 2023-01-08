package com.sunny.maven.rpc.test.spi;

import com.sunny.maven.rpc.spi.loader.ExtensionLoader;
import com.sunny.maven.rpc.test.spi.service.SpiService;
import org.junit.Test;

/**
 * @author SUNNY
 * @description: SPI测试类
 * @create: 2023-01-08 19:54
 */
public class SpiTest {

    @Test
    public void testSpiLoader() {
        SpiService spiService = ExtensionLoader.getExtension(SpiService.class, "spiService");
        String result = spiService.hello("sunny");
        System.out.println(result);
    }
}
