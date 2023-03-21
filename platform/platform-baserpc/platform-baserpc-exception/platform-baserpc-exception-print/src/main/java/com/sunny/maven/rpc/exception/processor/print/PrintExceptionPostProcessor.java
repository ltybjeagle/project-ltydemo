package com.sunny.maven.rpc.exception.processor.print;

import com.sunny.maven.rpc.exception.processor.ExceptionPostProcessor;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SUNNY
 * @description: 打印异常
 * @create: 2023-03-20 13:51
 */
@Slf4j
@SPIClass
public class PrintExceptionPostProcessor implements ExceptionPostProcessor {
    @Override
    public void postExceptionProcessor(Throwable e) {
        log.info("程序抛出异常===>>>{}", e.getMessage());
    }
}
