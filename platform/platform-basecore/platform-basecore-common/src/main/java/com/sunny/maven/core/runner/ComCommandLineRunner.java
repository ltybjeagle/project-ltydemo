package com.sunny.maven.core.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @author SUNNY
 * @description: 系统任务启动
 * @create: 2023/7/21 16:30
 */
@Slf4j
@Component
@Order(1)
public class ComCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("CommandLineRunner>>>" + Arrays.toString(args));
    }
}
