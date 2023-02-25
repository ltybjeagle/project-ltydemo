package com.sunny.maven.rpc.test.consumer;

import com.sunny.maven.rpc.consumer.RpcClient;
import com.sunny.maven.rpc.proxy.api.async.IAsyncObjectProxy;
import com.sunny.maven.rpc.proxy.api.future.RpcFuture;
import com.sunny.maven.rpc.test.api.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

/**
 * @author SUNNY
 * @description: 测试Java原生启动服务消费者
 * @create: 2023-01-01 22:59
 */
@Slf4j
public class RpcConsumerNativeTest {

    public static void main(String[] args) throws Exception {
        RpcClient rpcClient = new RpcClient("127.0.0.1:2181", "zookeeper",
                "1.0.0", "SUNNY", "protostuff",
                "enhanced_leastconnections", 3000, "asm", false,
                false, 30000, 60000, 1000,
                3, true, 30000, false,
                "127.0.0.1:27880");
        DemoService demoService = rpcClient.create(DemoService.class);
        String result = demoService.hello("SUNNY");
        log.info("返回的结果数据===>>> {}", result);
        rpcClient.shutdown();
    }

    private RpcClient rpcClient;

    /**
     * zookeeper：127.0.0.1:2181
     * nacos：127.0.0.1:8848
     */
    @Before
    public void initRpcClient() {
        rpcClient = new RpcClient("127.0.0.1:2181", "zookeeper", "1.0.0",
                "SUNNY", "protostuff",
                "enhanced_leastconnections", 3000, "asm", false,
                false, 30000, 60000, 1000,
                3, true, 30000, false,
                "127.0.0.1:27880");
    }

    @Test
    public void testInterfaceRpc() throws Exception {
        DemoService demoService = rpcClient.create(DemoService.class);
        String result = demoService.hello("SUNNY");
        log.info("返回的结果数据===>>> {}", result);
        Thread.sleep(600000);
        rpcClient.shutdown();
    }

    @Test
    public void testAsyncInterfaceRpc() throws Exception {
        IAsyncObjectProxy demoService = rpcClient.createAsync(DemoService.class);
        RpcFuture future = demoService.call("hello", "SUNNY");
        log.info("返回的结果数据===>>> {}", future.get());
        rpcClient.shutdown();
    }
}
