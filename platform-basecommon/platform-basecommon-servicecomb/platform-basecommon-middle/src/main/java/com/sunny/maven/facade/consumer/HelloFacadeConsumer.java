package com.sunny.maven.facade.consumer;

import com.sunny.maven.facade.HelloFacade;
import org.apache.servicecomb.provider.pojo.RpcReference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * @author SUNNY
 * @description: 消费端
 * @create: 2021-08-31 16:56
 */
@Service
public class HelloFacadeConsumer implements HelloFacade {
    private static final Logger logger = LoggerFactory.getLogger(HelloFacadeConsumer.class);

    @RpcReference(microserviceName = "provider-service", schemaId = "service-hello")
    private HelloFacade helloFacade;

    /**
     * 获取服务对象
     * @return HelloFacade
     */
    public HelloFacade getHelloFacade() {
        return helloFacade;
    }

    /**
     * 请求接口
     * @param name 参数
     * @return 响应
     */
    @Override
    public String sayHello(String name) throws Exception {
        logger.info("进入middle.service, 参数：{}", name);
        String result = getHelloFacade().sayHello(name);
        logger.info("middle.service, 结果：{}", result);
        return result;
    }
}
