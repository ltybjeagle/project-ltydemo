package com.sunny.maven.rpc.protocol;

import com.sunny.maven.rpc.protocol.header.RpcHeader;
import com.sunny.maven.rpc.protocol.header.RpcHeaderFactory;
import com.sunny.maven.rpc.protocol.request.RpcRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author SUNNY
 * @description: 测试
 * @create: 2022-12-27 17:44
 */
@Slf4j
public class RpcProtocolTest {

    @Test
    public void rpcProtocol() {
        RpcHeader header = RpcHeaderFactory.getRequestHeader("jdk");
        RpcRequest body = new RpcRequest();
        body.setOneway(false);
        body.setAsync(false);
        body.setClassName("com.sunny.maven.rpc.protocol");
        body.setMethodName("hello");
        body.setGroup("SUNNY");
        body.setParameterTypes(new Class[]{String.class});
        body.setParameters(new Object[]{"SUNNY"});
        body.setVersion("1.0.0");
        RpcProtocol<RpcRequest> protocol = new RpcProtocol<>();
        protocol.setHeader(header);
        protocol.setBody(body);
        log.info("RpcProtocol<RpcRequest> ===>> {}", protocol.toString());
    }
}
