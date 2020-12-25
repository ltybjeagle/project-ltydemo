package com.sunny.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/23 11:20
 * @description：Zuul网关
 * @modified By：
 * @version: 1.0.0
 */
@EnableZuulProxy
@SpringBootApplication
public class ZuulApplication {
    public static void main(String ...args) throws Exception {
        SpringApplication.run(ZuulApplication.class, args);
    }
}
