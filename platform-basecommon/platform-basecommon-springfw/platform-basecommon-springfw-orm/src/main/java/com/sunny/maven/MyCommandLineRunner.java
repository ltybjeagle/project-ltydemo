package com.sunny.maven;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author ：SUNNY
 * @date ：Created in 2021/1/5 15:51
 * @description：自定义扩展
 * @modified By：
 * @version: 1.0.0
 */
@Component
@Order(1)
public class MyCommandLineRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("#####MyCommandLineRunner execute!#####");
    }
}
