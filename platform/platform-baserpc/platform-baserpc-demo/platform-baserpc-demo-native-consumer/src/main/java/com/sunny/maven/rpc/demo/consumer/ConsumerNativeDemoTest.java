package com.sunny.maven.rpc.demo.consumer;

import com.sunny.maven.rpc.consumer.RpcClient;
import com.sunny.maven.rpc.demo.api.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;

/**
 * @author SUNNY
 * @description: 服务消费者
 * @create: 2023-02-14 17:23
 */
@Slf4j
public class ConsumerNativeDemoTest {

    private RpcClient rpcClient;

    /**
     * zookeeper：127.0.0.1:2181
     * nacos：127.0.0.1:8848
     */
    @Before
    public void initRpcClient() {
        rpcClient = new RpcClient("127.0.0.1:2181", "zookeeper", "1.0.0",
                "SUNNY", "protostuff", "enhanced_leastconnections",
                3000, "asm", false, false,
                30000, 60000, 1000, 3,
                true, 30000);
    }

    @Test
    public void testInterfaceRpc() throws Exception {
        DemoService demoService = rpcClient.create(DemoService.class);
        for (int i = 0; i < 5; i++) {
            String result = demoService.hello("SUNNY");
            log.info("返回的结果数据===>>> {}", result);
        }
        while (true) {
            Thread.sleep(1000);
        }
    }
}
