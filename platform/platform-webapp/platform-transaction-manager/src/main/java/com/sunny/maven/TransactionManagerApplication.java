package com.sunny.maven;

import com.codingapi.txlcn.tm.config.EnableTransactionManagerServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @description:
 * @author: Sunny
 * @date: 2019/11/11
 */
@SpringBootApplication
@EnableTransactionManagerServer
public class TransactionManagerApplication {
    public static void main(String ...args) {
        SpringApplication.run(TransactionManagerApplication.class, args);
    }
}
