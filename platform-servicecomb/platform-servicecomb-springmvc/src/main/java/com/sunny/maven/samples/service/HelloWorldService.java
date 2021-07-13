package com.sunny.maven.samples.service;

import org.apache.servicecomb.provider.rest.common.RestSchema;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/22 19:29
 * @description：接口服务
 * @modified By：
 * @version: 1.0.0
 */
@RestSchema(schemaId = "hello")
@RequestMapping("/")
public class HelloWorldService {
    @GetMapping("/hello")
    public String hello() {
        try {
            Thread.sleep(10000);
        } catch (Exception e) {

        }
        return "Hello world!";
    }
}
