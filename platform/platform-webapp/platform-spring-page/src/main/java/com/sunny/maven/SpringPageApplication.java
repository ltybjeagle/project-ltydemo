package com.sunny.maven;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/5/3 23:31
 * @description：前端服务启动类
 * @modified By：
 * @version: 1.0.0
 */
@SpringBootApplication
@ServletComponentScan(basePackages = {"com.sunny.maven.servlet"})
public class SpringPageApplication {
    public static void main(String ...args) throws Exception {
        SpringApplication.run(SpringPageApplication.class, args);
    }
}
