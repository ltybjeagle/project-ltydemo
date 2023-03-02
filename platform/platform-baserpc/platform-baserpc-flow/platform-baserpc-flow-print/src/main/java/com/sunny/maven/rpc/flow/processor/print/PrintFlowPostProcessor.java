package com.sunny.maven.rpc.flow.processor.print;

import com.sunny.maven.rpc.flow.processor.FlowPostProcessor;
import com.sunny.maven.rpc.protocol.header.RpcHeader;
import com.sunny.maven.rpc.spi.annotation.SPIClass;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SUNNY
 * @description: 打印处理
 * @create: 2023-03-01 11:10
 */
@Slf4j
@SPIClass
public class PrintFlowPostProcessor implements FlowPostProcessor {
    @Override
    public void postRpcHeaderProcessor(RpcHeader rpcHeader) {
        log.info(getRpcHeaderString(rpcHeader));
    }

    private String getRpcHeaderString(RpcHeader rpcHeader) {
        StringBuilder sb = new StringBuilder();
        sb.append("magic: ").append(rpcHeader.getMagic());
        sb.append(", requestId: ").append(rpcHeader.getRequestId());
        sb.append(", msgType: ").append(rpcHeader.getMsgType());
        sb.append(", serializationType: ").append(rpcHeader.getSerializationType());
        sb.append(", status: ").append(rpcHeader.getStatus());
        sb.append(", msgLen: ").append(rpcHeader.getMsgLen());

        return sb.toString();
    }
}
