package com.liuty.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Sunny
 * @date: 2019/11/30
 */
@RestController
@SpringBootApplication
public class SpringDemoApplication {

//    @RequestMapping("/")
//    public String hello() {
//        return "hello, spring security";
//    }

    public static void main(String ...args) throws Exception {
        SpringApplication.run(SpringDemoApplication.class, args);
    }
}
