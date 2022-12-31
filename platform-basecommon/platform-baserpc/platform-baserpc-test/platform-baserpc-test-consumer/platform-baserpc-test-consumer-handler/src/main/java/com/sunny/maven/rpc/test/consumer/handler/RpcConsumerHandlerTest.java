package com.sunny.maven.rpc.test.consumer.handler;

import com.sunny.maven.rpc.consumer.common.RpcConsumer;
import com.sunny.maven.rpc.protocol.RpcProtocol;
import com.sunny.maven.rpc.protocol.header.RpcHeaderFactory;
import com.sunny.maven.rpc.protocol.request.RpcRequest;

/**
 * @author SUNNY
 * @description: 测试服务消费者
 * @create: 2022-12-30 17:57
 */
public class RpcConsumerHandlerTest {

    public static void main(String[] args) throws Exception {
        RpcConsumer consumer = RpcConsumer.getInstance();
        consumer.sendRequest(getRpcRequestProtocol());
        Thread.sleep(2000);
        consumer.close();
    }

    private static RpcProtocol<RpcRequest> getRpcRequestProtocol() {
        // 模拟发送数据
        RpcProtocol<RpcRequest> protocol = new RpcProtocol<>();
        protocol.setHeader(RpcHeaderFactory.getRequestHeader("jdk"));
        RpcRequest request = new RpcRequest();
        request.setClassName("com.sunny.maven.rpc.test.api.DemoService");
        request.setMethodName("hello");
        request.setGroup("SUNNY");
        request.setParameterTypes(new Class[]{String.class});
        request.setParameters(new Object[]{"SUNNY"});
        request.setVersion("1.0.0");
        request.setOneway(false);
        request.setAsync(false);
        protocol.setBody(request);
        return protocol;
    }
}
