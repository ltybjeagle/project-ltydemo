package com.sunny.maven;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ：SUNNY
 * @date ：Created in 2021/1/5 15:47
 * @description：参数BEAN(SpringApplication启动的应用参数)
 * @modified By：
 * @version: 1.0.0
 */
@Component
public class ArgBean {
    @Autowired
    public ArgBean(ApplicationArguments args) {
        boolean debug = args.containsOption("debug");
        List<String> files = args.getNonOptionArgs();
    }
}
