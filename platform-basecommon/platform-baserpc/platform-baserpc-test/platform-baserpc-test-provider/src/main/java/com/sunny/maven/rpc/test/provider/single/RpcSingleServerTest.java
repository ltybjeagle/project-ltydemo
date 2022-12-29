package com.sunny.maven.rpc.test.provider.single;

import com.sunny.maven.rpc.provider.RpcSingleServer;
import org.junit.Test;

/**
 * @author SUNNY
 * @description: 测试Java原生启动RPC
 * @create: 2022-12-27 11:32
 */
public class RpcSingleServerTest {

    @Test
    public void startRpcSingleServer() {
        RpcSingleServer singleServer =
                new RpcSingleServer("127.0.0.1:27880", "com.sunny.maven.rpc.test");
        singleServer.startNettyServer();
    }
}
