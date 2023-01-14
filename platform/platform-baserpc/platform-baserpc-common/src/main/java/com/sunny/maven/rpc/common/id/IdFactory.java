package com.sunny.maven.rpc.common.id;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author SUNNY
 * @description: 简易ID工厂类
 * @create: 2022-12-27 17:35
 */
public class IdFactory {
    private static final AtomicLong REQUEST_ID_GEN = new AtomicLong(0);

    public static Long getId() {
        return REQUEST_ID_GEN.incrementAndGet();
    }
}
