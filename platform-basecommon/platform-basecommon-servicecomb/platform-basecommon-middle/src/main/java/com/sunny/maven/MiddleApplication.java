package com.sunny.maven;

import org.apache.servicecomb.springboot2.starter.EnableServiceComb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author SUNNY
 * @description: 中台服务
 * @create: 2021-09-03 22:16
 */
@SpringBootApplication
@EnableServiceComb
public class MiddleApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiddleApplication.class, args);
    }

}
