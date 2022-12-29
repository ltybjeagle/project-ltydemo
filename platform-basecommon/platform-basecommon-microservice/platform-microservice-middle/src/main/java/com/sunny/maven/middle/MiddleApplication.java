package com.sunny.maven.middle;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author SUNNY
 * @description: 服务启动类
 * @create: 2022-10-09 14:29
 */
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(value = { "com.sunny.maven.middle.user.mapper", "com.sunny.maven.middle.scheduled.mapper" })
public class MiddleApplication {
    public static void main(String[] args) {
        SpringApplication.run(MiddleApplication.class, args);
    }
}
