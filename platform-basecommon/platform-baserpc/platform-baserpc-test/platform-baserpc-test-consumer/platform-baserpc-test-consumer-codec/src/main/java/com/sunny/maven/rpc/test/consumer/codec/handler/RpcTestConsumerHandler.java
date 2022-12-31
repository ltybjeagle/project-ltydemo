package com.sunny.maven.rpc.test.consumer.codec.handler;

import com.alibaba.fastjson.JSONObject;
import com.sunny.maven.rpc.protocol.RpcProtocol;
import com.sunny.maven.rpc.protocol.header.RpcHeaderFactory;
import com.sunny.maven.rpc.protocol.request.RpcRequest;
import com.sunny.maven.rpc.protocol.response.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SUNNY
 * @description: RPC消费者处理器
 * @create: 2022-12-29 14:06
 */
@Slf4j
public class RpcTestConsumerHandler extends SimpleChannelInboundHandler<RpcProtocol<RpcResponse>> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        log.info("发送数据开始...");
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
        log.info("服务消费者发送的数据====>>> {}", JSONObject.toJSONString(protocol));
        ctx.writeAndFlush(protocol);
        log.info("发送数据完毕...");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcProtocol<RpcResponse> protocol)
            throws Exception {
        log.info("服务消费者收到的数据为====>>> {}", JSONObject.toJSONString(protocol));
    }
}
