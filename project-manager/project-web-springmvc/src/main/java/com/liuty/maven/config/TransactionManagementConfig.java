package com.liuty.maven.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * @Configuration 配置类
 * @EnableTransactionManagement 启用事务管理
 * @Order(10) 存在多配置类的时候，可以使用此注解进行配置类排序加载顺序
 * 系统启动初始化事务管理器配置
 */
@Configuration
@EnableTransactionManagement
@Order(10)
public class TransactionManagementConfig implements TransactionManagementConfigurer {
    private static final Logger logger = LoggerFactory.getLogger(TransactionManagementConfig.class);
    public TransactionManagementConfig() {
        logger.info("TransactionManagementConfig启动......");
    }

    @Autowired
    private DataSource dataSource;

    /**
     * 设置事务管理器，在需要事务管理的地方使用注解添加事务，保证系列操作的原子性
     * 类型：
     * 1、DataSourceTransactionManager: JDBC事务管理器
     * 2、JtaTransactionManager: 分布式事务管理器，支持跨数据源
     * @return
     */
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
