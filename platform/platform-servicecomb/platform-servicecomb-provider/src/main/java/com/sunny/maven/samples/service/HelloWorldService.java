package com.sunny.maven.samples.service;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/9/7 20:01
 * @description：HelloWorld服务提供类
 * @modified By：
 * @version: 1.0.0
 */
@RestSchema(schemaId = "hello")
@RequestMapping("/hello")
public class HelloWorldService {

    @GetMapping("/hello")
    public String hello() {
        return "Hello world!";
    }
}
