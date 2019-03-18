package com.liuty.maven.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类注解说明：
 *      1、注解@Configuration：标注此类是配置类
 *
 * 初始化实体：
 *      1、MapperScannerConfigurer
 *
 * 注：
 *      1、MapperScannerConfigurer类实现了BeanDefinitionRegistryPostProcessor接口，优先其他配置类初始化，
 *      因此单独出来初始化
 */
@Configuration
public class MyBatisConfig {
    private static final Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);
    public MyBatisConfig() {
        logger.info("MyBatisConfig启动......");
    }

    /**
     * MapperScannerConfigurer：启动初始化MyBatis加载接口类，通过MapperScannerConfigurer自动扫描加载所有接口类
     *      1、注册Mapper Bean自动扫描DAO映射接口类（可使用注解@Mapper形式替代）
     *      2、接口类跟mapper映射配合文件对应
     */
    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.liuty.maven.dao");
        return mapperScannerConfigurer;
    }
}
