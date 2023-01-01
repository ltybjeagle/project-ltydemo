package com.sunny.maven.rpc.test.consumer;

import com.sunny.maven.rpc.consumer.RpcClient;
import com.sunny.maven.rpc.test.api.DemoService;
import lombok.extern.slf4j.Slf4j;

/**
 * @author SUNNY
 * @description: 测试Java原生启动服务消费者
 * @create: 2023-01-01 22:59
 */
@Slf4j
public class RpcConsumerNativeTest {

    public static void main(String[] args) throws Exception {
        RpcClient rpcClient = new RpcClient("1.0.0", "SUNNY", "JDK",
                3000, false, false);
        DemoService demoService = rpcClient.create(DemoService.class);
        String result = demoService.hello("SUNNY");
        log.info("返回的结果数据===>>> {}", result);
        rpcClient.shutdown();
    }
}
