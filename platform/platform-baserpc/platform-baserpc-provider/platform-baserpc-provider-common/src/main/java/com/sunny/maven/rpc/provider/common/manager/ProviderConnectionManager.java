package com.sunny.maven.rpc.provider.common.manager;

import com.sunny.maven.rpc.constants.RpcConstants;
import com.sunny.maven.rpc.protocol.RpcProtocol;
import com.sunny.maven.rpc.protocol.enumeration.RpcType;
import com.sunny.maven.rpc.protocol.header.RpcHeader;
import com.sunny.maven.rpc.protocol.header.RpcHeaderFactory;
import com.sunny.maven.rpc.protocol.response.RpcResponse;
import com.sunny.maven.rpc.provider.common.cache.ProviderChannelCache;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @author SUNNY
 * @description: 服务提供者连接管理器
 * @create: 2023-02-11 11:38
 */
@Slf4j
public class ProviderConnectionManager {

    /**
     * 扫描并移除不活跃的连接
     */
    public static void scanNotActiveChannel() {
        Set<Channel> channelCache = ProviderChannelCache.getChannelCache();
        if (channelCache == null || channelCache.isEmpty()) {
            return;
        }
        channelCache.forEach(channel -> {
            if (!channel.isOpen() || !channel.isActive()) {
                channel.close();
                ProviderChannelCache.remove(channel);
            }
        });
    }

    /**
     * 发送ping消息
     */
    public static void broadcastPingMessageFromProvider() {
        Set<Channel> channelCache = ProviderChannelCache.getChannelCache();
        if (channelCache == null || channelCache.isEmpty()) {
            return;
        }
        RpcHeader header = RpcHeaderFactory.getRequestHeader(RpcConstants.SERIALIZATION_PROTOSTUFF,
                RpcType.HEARTBEAT_FROM_PROVIDER.getType());
        RpcProtocol<RpcResponse> responseRpcProtocol = new RpcProtocol<>();
        RpcResponse response = new RpcResponse();
        response.setResult(RpcConstants.HEARTBEAT_PING);
        responseRpcProtocol.setHeader(header);
        responseRpcProtocol.setBody(response);
        channelCache.forEach(channel -> {
            if (channel.isOpen() && channel.isActive()) {
                log.info("send heartbeat message to service consumer, the consumer is:{}, the heartbeat message is:{}",
                        channel.remoteAddress(), response.getResult());
                channel.writeAndFlush(responseRpcProtocol);
            }
        });
    }
}
