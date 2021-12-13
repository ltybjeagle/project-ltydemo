package com.sunny.maven.controller;

import com.sunny.maven.common.MyLogger;
import com.sunny.maven.template.MyCacheTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author SUNNY
 * @description: Hello测试类
 * @create: 2021-11-22 22:29
 */
@RestController
public class HelloController {

    /**
     * 环境变量
     */
    private Environment env;
    private MyLogger myLogger;
    private MyCacheTemplate myCacheTemplate;

    @GetMapping("/hello")
    public String hello() {
        StopWatch stopWatch = new StopWatch("/hello");
        stopWatch.start();
        System.out.println(env.getProperty("com.sunny.name"));
        myLogger.saveLog();
        System.out.println("redis:" + myCacheTemplate.getRedisName());
        stopWatch.stop();
        System.out.println("time:" + stopWatch.getTotalTimeMillis());
        return "hello";
    }
    /**
     * 注入对象
     */
    @Autowired
    public HelloController(Environment env, MyLogger myLogger, MyCacheTemplate myCacheTemplate) {
        this.env = env;
        this.myLogger = myLogger;
        this.myCacheTemplate = myCacheTemplate;
    }
}
