package com.sunny.maven.rpc.test.provider.single;

import com.sunny.maven.rpc.provider.RpcSingleServer;
import org.junit.Before;
import org.junit.Test;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author SUNNY
 * @description: 测试Java原生启动RPC
 * @create: 2022-12-27 11:32
 */
public class RpcSingleServerTest {

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

    @Test
    public void startRpcSingleServer() {
        RpcSingleServer singleServer =
                new RpcSingleServer("127.0.0.1:27880", "com.sunny.maven.rpc.test",
                        "cglib", "127.0.0.1:2181", "zookeeper");
        singleServer.startNettyServer();
    }
}