package com.sunny.maven.facade.provider;

import com.sunny.maven.facade.HelloFacade;
import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author SUNNY
 * @description: 服务提供端
 * @create: 2021-08-21 16:17
 */
@RestSchema(schemaId = "middle-hello")
@RequestMapping(path = "/middle/")
public class HelloMiddleProvider implements HelloFacade {
    private static final Logger logger = LoggerFactory.getLogger(HelloMiddleProvider.class);
    /**
     * 逻辑对象
     */
    private HelloFacade helloFacade;

    /**
     * 请求接口
     * @param name 参数
     * @return 响应
     */
    @Override
    @RequestMapping(path = "hello", method = RequestMethod.GET)
    public String sayHello(@RequestParam(name = "name") String name) throws Exception {
        logger.info("进入middle.provider, 参数：{}", name);
        String result = helloFacade.sayHello(name);
        logger.info("middle.provider返回, 结果：{}", result);
        return result;
    }

    /**
     * 构造函数
     * @param helloFacade 注入服务对象
     */
    public HelloMiddleProvider(@Qualifier("helloFacadeConsumer") @Autowired HelloFacade helloFacade) {
        this.helloFacade = helloFacade;
    }
}
