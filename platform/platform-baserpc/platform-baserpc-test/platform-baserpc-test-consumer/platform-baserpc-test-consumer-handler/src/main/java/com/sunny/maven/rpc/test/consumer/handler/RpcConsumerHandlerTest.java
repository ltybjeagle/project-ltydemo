package com.sunny.maven.rpc.test.consumer.handler;

import com.sunny.maven.rpc.common.exception.RegistryException;
import com.sunny.maven.rpc.consumer.common.RpcConsumer;
import com.sunny.maven.rpc.protocol.enumeration.RpcType;
import com.sunny.maven.rpc.proxy.api.callback.AsyncRpcCallback;
import com.sunny.maven.rpc.proxy.api.future.RpcFuture;
import com.sunny.maven.rpc.protocol.RpcProtocol;
import com.sunny.maven.rpc.protocol.header.RpcHeaderFactory;
import com.sunny.maven.rpc.protocol.request.RpcRequest;
import com.sunny.maven.rpc.registry.api.RegistryService;
import com.sunny.maven.rpc.registry.api.config.RegistryConfig;
import com.sunny.maven.rpc.spi.loader.ExtensionLoader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author SUNNY
 * @description: 测试服务消费者
 * @create: 2022-12-30 17:57
 */
@Slf4j
public class RpcConsumerHandlerTest {

    public static void main(String[] args) throws Exception {
        RpcConsumer consumer = RpcConsumer.getInstance(30000, 60000,
                1000, 3);
        RpcFuture future = consumer.sendRequest(getRpcRequestProtocol(), getRegistryService(
                "127.0.0.1:2181", "zookeeper", "random"));
//        RpcFuture future = RpcContext.getContext().getRpcFuture();
//        log.info("从服务消费者获取的数据====>>> {}", future.get());
//        log.info("无需获取返回的结果数据");
        future.addCallback(new AsyncRpcCallback() {
            @Override
            public void onSuccess(Object result) {
                log.info("从服务消费者获取的数据====>>> {}", result);
            }

            @Override
            public void onException(Exception e) {
                log.info("抛出的异常====>>> {}", e.getMessage());
            }
        });
        Thread.sleep(2000);
        consumer.close();
    }

    //TODO 修改
    private static RegistryService getRegistryService(String registryAddress, String registryType, String registryLoadBalanceType) {
        if (StringUtils.isEmpty(registryType)){
            throw new IllegalArgumentException("registry type is null");
        }
        RegistryService registryService = ExtensionLoader.getExtension(RegistryService.class, registryType);
        try {
            registryService.init(new RegistryConfig(registryAddress, registryType, registryLoadBalanceType));
        } catch (Exception e) {
            log.error("RpcClient init registry service throws exception:{}", e.getMessage());
            throw new RegistryException(e.getMessage(), e);
        }
        return registryService;
    }


    private static RpcProtocol<RpcRequest> getRpcRequestProtocol() {
        // 模拟发送数据
        RpcProtocol<RpcRequest> protocol = new RpcProtocol<>();
        protocol.setHeader(RpcHeaderFactory.getRequestHeader("jdk", RpcType.REQUEST.getType()));
        RpcRequest request = new RpcRequest();
        request.setClassName("com.sunny.maven.rpc.test.api.DemoService");
        request.setMethodName("hello");
        request.setGroup("SUNNY");
        request.setParameterTypes(new Class[]{String.class});
        request.setParameters(new Object[]{"SUNNY"});
        request.setVersion("1.0.0");
        request.setOneway(false);
        request.setAsync(false);
        protocol.setBody(request);
        return protocol;
    }
}
