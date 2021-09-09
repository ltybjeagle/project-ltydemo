package com.sunny.maven;

import org.apache.servicecomb.springboot2.starter.EnableServiceComb;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author SUNNY
 * @description: 服务端
 * @create: 2021-08-20 19:46
 */
@SpringBootApplication
@EnableServiceComb
public class ServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }
}
