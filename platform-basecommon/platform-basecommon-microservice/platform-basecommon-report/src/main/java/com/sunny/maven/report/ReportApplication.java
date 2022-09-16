package com.sunny.maven.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

/**
 * @author SUNNY
 * @description: 报表服务启动类
 * @create: 2022-06-17 21:41
 */
@SpringBootApplication(scanBasePackages = {"org.jeecg.modules.jmreport", "com.sunny.maven.report"},
        exclude = {MongoAutoConfiguration.class})
public class ReportApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReportApplication.class, args);
    }
}
