package com.sunny.maven.rpc.flow.processor;

import com.sunny.maven.rpc.constants.RpcConstants;
import com.sunny.maven.rpc.protocol.header.RpcHeader;
import com.sunny.maven.rpc.spi.annotation.SPI;

/**
 * @author SUNNY
 * @description: 流量分析后置处理器接口
 * @create: 2023-03-01 11:03
 */
@SPI(value = RpcConstants.FLOW_POST_PROCESSOR_PRINT)
public interface FlowPostProcessor {

    /**
     * 流控分析后置处理器方法
     */
    void postRpcHeaderProcessor(RpcHeader rpcHeader);
}
