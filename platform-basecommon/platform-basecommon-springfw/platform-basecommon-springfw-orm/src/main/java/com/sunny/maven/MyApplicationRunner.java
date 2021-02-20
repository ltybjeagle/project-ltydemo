package com.sunny.maven;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author ：SUNNY
 * @date ：Created in 2021/1/5 15:53
 * @description：自定义扩展
 * @modified By：
 * @version: 1.0.0
 */
@Component
public class MyApplicationRunner implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("#####MyApplicationRunner execute!#####");
    }
}
