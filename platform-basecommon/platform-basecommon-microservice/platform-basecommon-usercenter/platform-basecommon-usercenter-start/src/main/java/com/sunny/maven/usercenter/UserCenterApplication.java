package com.sunny.maven.usercenter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author SUNNY
 * @description: 用户中心启动类
 * @create: 2022-01-12 09:40
 */
@SpringBootApplication
public class UserCenterApplication {
    private static final Logger logger = LoggerFactory.getLogger(UserCenterApplication.class);
    public static void main(String[] args) {
        // 注册一个JVM关闭的钩子
        Runtime.getRuntime().addShutdownHook(new Thread(() -> logger.info("进程关闭......")));
        SpringApplication.run(UserCenterApplication.class, args);
    }
}
