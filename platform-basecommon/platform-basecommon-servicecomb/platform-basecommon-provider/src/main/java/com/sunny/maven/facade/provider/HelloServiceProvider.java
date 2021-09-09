package com.sunny.maven.facade.provider;

import com.sunny.maven.facade.HelloFacade;
import com.sunny.maven.service.HelloService;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author SUNNY
 * @description: 服务提供端
 * @create: 2021-08-21 16:17
 */
@RestSchema(schemaId = "service-hello")
@RequestMapping(path = "/service/")
public class HelloServiceProvider implements HelloFacade {
    private static final Logger logger = LoggerFactory.getLogger(HelloServiceProvider.class);
    /**
     * 逻辑对象
     */
    private HelloService helloService;

    /**
     * 请求接口
     * @param name 参数
     * @return 响应
     */
    @Override
    @RequestMapping(path = "hello", method = RequestMethod.GET)
    public String sayHello(@RequestParam(name = "name") String name) throws Exception {
        logger.info("进入service.provider, 参数：{}", name);
        String result = helloService.sayHello(name);
        logger.info("service.provider返回, 结果：{}", result);
        return result;
    }

    /**
     * 构造函数
     * @param helloService 注入服务对象
     */
    public HelloServiceProvider(@Autowired HelloService helloService) {
        this.helloService = helloService;
    }
}
