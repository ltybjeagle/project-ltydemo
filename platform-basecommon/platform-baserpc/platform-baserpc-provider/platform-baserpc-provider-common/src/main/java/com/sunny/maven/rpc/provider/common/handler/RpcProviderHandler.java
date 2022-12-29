package com.sunny.maven.rpc.provider.common.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @author SUNNY
 * @description: RPC服务提供者的Handler处理类
 * @create: 2022-12-26 17:29
 */
@Slf4j
public class RpcProviderHandler extends SimpleChannelInboundHandler<Object> {
    private final Map<String, Object> handlerMap;
    public RpcProviderHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
        log.info("RPC提供者收到的数据为====>>> {}", o.toString());
        log.info("handlerMap中存放的数据如下所示：");
        for (Map.Entry<String, Object> entry : handlerMap.entrySet()) {
            log.info("{} === {}", entry.getKey(), entry.getValue());
        }
        ctx.writeAndFlush(o);
    }
}
