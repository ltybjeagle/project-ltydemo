package com.sunny.maven.rpc.provider.common.cache;

import io.netty.channel.Channel;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author SUNNY
 * @description: 服务提供者缓存连接成功的Channel
 * @create: 2023-02-11 11:33
 */
public class ProviderChannelCache {
    private static volatile Set<Channel> channelCache = new CopyOnWriteArraySet<>();

    public static void add(Channel channel) {
        channelCache.add(channel);
    }

    public static void remove(Channel channel) {
        channelCache.remove(channel);
    }

    public static Set<Channel> getChannelCache() {
        return channelCache;
    }
}
