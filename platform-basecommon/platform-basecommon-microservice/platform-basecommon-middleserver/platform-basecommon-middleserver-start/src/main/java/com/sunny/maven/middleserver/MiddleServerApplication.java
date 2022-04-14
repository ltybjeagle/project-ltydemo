package com.sunny.maven.middleserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author SUNNY
 * @description: 公共服务系统
 * @create: 2022-04-13 16:11
 */
@SpringBootApplication
public class MiddleServerApplication {
    private static final Logger logger = LoggerFactory.getLogger(MiddleServerApplication.class);
    public static void main(String[] args) {
        // 注册一个JVM关闭的钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> logger.info("进程关闭......")));
        SpringApplication.run(MiddleServerApplication.class, args);
    }
}
