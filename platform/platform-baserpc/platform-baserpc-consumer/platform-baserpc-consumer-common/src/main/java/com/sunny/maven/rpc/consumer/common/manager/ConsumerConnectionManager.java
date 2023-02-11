package com.sunny.maven.rpc.consumer.common.manager;

import com.sunny.maven.rpc.constants.RpcConstants;
import com.sunny.maven.rpc.consumer.common.cache.ConsumerChannelCache;
import com.sunny.maven.rpc.protocol.RpcProtocol;
import com.sunny.maven.rpc.protocol.enumeration.RpcType;
import com.sunny.maven.rpc.protocol.header.RpcHeader;
import com.sunny.maven.rpc.protocol.header.RpcHeaderFactory;
import com.sunny.maven.rpc.protocol.request.RpcRequest;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.util.Set;

/**
 * @author SUNNY
 * @description: 服务消费者连接管理器
 * @create: 2023-02-10 15:16
 */
@Slf4j
public class ConsumerConnectionManager {
    /**
     * 扫描并移除不活跃的连接
     */
    public static void scanNotActiveChannel() {
        Set<Channel> channelChannel = ConsumerChannelCache.getChannelCache();
        if (channelChannel == null || channelChannel.isEmpty()) {
            return;
        }
        channelChannel.forEach(channel -> {
            if (!channel.isOpen() || !channel.isActive()) {
                channel.close();
                ConsumerChannelCache.remove(channel);
            }
        });
    }

    /**
     * 发送ping消息
     */
    public static void broadcastPingMessageFromConsumer() {
        Set<Channel> channelChannel = ConsumerChannelCache.getChannelCache();
        if (channelChannel == null || channelChannel.isEmpty()) {
            return;
        }
        RpcHeader header = RpcHeaderFactory.getRequestHeader(RpcConstants.SERIALIZATION_PROTOSTUFF,
                RpcType.HEARTBEAT_FROM_CONSUMER.getType());
        RpcProtocol<RpcRequest> requestRpcProtocol = new RpcProtocol<>();
        RpcRequest request = new RpcRequest();
        request.setParameters(new Object[] {RpcConstants.HEARTBEAT_PING});
        requestRpcProtocol.setHeader(header);
        requestRpcProtocol.setBody(request);
        channelChannel.forEach(channel -> {
            if (channel.isOpen() && channel.isActive()) {
                log.info("send heartbeat message to service provider, the provider is:{}, the heartbeat message is:{}",
                        channel.remoteAddress(), RpcConstants.HEARTBEAT_PING);
                channel.writeAndFlush(requestRpcProtocol);
            }
        });
    }
}
