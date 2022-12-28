package com.sunny.maven.core.extend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

/**
 * @author SUNNY
 * @description: CommandLineRunner 整个项目启动完毕后，自动执行
 *               用户扩展此接口，进行启动项目之后一些业务的预处理
 * @create: 2022-10-18 16:38
 */
@Slf4j
public class ExtCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        log.info("20.[CommandLineRunner]");
    }
}
