package com.sunny.maven.microservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author SUNNY
 * @description: 商品服务启动类
 * @create: 2023-03-23 11:25
 */
@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@MapperScan(value = {"com.sunny.maven.microservice.product.mapper"})
public class ProductStarter {
    public static void main(String[] args) {
        SpringApplication.run(ProductStarter.class, args);
    }
}
