package com.sunny.maven.rpc.exception.processor;

import com.sunny.maven.rpc.constants.RpcConstants;
import com.sunny.maven.rpc.spi.annotation.SPI;

/**
 * @author SUNNY
 * @description: 异常信息后置处理器
 * @create: 2023-03-20 13:47
 */
@SPI(value = RpcConstants.EXCEPTION_POST_PROCESSOR_PRINT)
public interface ExceptionPostProcessor {
    /**
     * 处理异常信息，进行统计等
     */
    void postExceptionProcessor(Throwable e);
}
