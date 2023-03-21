package com.sunny.maven.rpc.demo.provider;

import com.sunny.maven.rpc.provider.RpcSingleServer;
import org.junit.Before;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author SUNNY
 * @description: 服务提供者
 * @create: 2023-02-24 12:15
 */
public class ProviderNativeDemo {
    @Before
    public void disableWarning() {
        try {
            Field theUnSafe = Unsafe.class.getDeclaredField("theUnsafe");
            theUnSafe.setAccessible(true);
            Unsafe u = (Unsafe) theUnSafe.get(null);

            Class cls = Class.forName("jdk.internal.module.IllegalAccessLogger");
            Field logger = cls.getDeclaredField("logger");
            u.putObjectVolatile(cls, u.staticFieldOffset(logger), null);
        } catch (Exception e) {
        }
    }

    /**
     * zookeeper：127.0.0.1:2181
     * nacos：127.0.0.1:8848
     */
    @Test
    public void startRpcSingleServer() {
        RpcSingleServer singleServer =
                new RpcSingleServer("127.0.0.1:27880", "127.0.0.1:27880",
                        "com.sunny.maven.rpc", "asm", "127.0.0.1:2181",
                        "zookeeper", "random", 3000,
                        6000, false, 30000,
                        16, 16, "print", 1,
                        "first", false, 2, false,
                        "guava", 1, 5000, "fallback",
                        false, "percent", 1, 5000,
                        "print");
        singleServer.startNettyServer();
    }
}
