package com.sunny.maven.rpc.consumer.spring.config;

import org.springframework.context.ApplicationContext;

/**
 * @author SUNNY
 * @description: Spring上下文
 * @create: 2023-02-24 15:36
 */
public class RpcConsumerSpringContext {
    /**
     * Spring ApplicationContext
     */
    private ApplicationContext context;

    private RpcConsumerSpringContext() {
    }

    private static class Holder {
        private static final RpcConsumerSpringContext INSTANCE = new RpcConsumerSpringContext();
    }

    public static RpcConsumerSpringContext getInstance() {
        return Holder.INSTANCE;
    }

    public ApplicationContext getContext() {
        return context;
    }

    public void setContext(ApplicationContext context) {
        this.context = context;
    }
}
