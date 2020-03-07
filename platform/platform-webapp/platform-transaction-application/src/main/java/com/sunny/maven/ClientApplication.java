package com.sunny.maven;

import com.codingapi.txlcn.tc.config.EnableDistributedTransaction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @description:
 * @author: Sunny
 * @date: 2019/11/11
 */
@SpringBootApplication
@RestController
@EnableDistributedTransaction
public class ClientApplication {
    public static void main(String ...args) {
        SpringApplication.run(ClientApplication.class, args);
    }

    @RequestMapping()
    public String insertInfo() {
        return null;
    }
}
