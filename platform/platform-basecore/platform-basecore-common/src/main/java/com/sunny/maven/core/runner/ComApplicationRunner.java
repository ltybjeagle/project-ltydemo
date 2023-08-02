package com.sunny.maven.core.runner;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: 系统任务启动
 * @create: 2023/7/21 16:38
 */
@Slf4j
@Component
@Order(1)
public class ComApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        log.info("ApplicationRunner>>>NonOptionArgs>>>" + args.getNonOptionArgs());
        log.info("ApplicationRunner>>>OptionNames>>>" + args.getOptionNames());
    }
}
