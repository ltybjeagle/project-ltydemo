package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/12/20 14:23
 * @description：Zuul
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
