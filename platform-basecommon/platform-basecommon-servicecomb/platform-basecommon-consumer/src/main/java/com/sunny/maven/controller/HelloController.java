package com.sunny.maven.controller;

import com.sunny.maven.facade.HelloFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUNNY
 * @description: 请求控制器
 * @create: 2021-09-04 10:01
 */
@RestController
public class HelloController {
    private static final Logger logger = LoggerFactory.getLogger(HelloController.class);

    /**
     * 逻辑对象
     */
    private HelloFacade helloFacade;

    @RequestMapping(path = "/web/hello", method = RequestMethod.GET)
    public String sayHello(@RequestParam(name = "name") String name) throws Exception {
        logger.info("进入web.controller，参数：{}", name);
        String result = helloFacade.sayHello(name);
        logger.info("web.controller返回，结果：{}", result);
        return result;
    }

    public HelloController(@Autowired HelloFacade helloFacade) {
        this.helloFacade = helloFacade;
    }
}
