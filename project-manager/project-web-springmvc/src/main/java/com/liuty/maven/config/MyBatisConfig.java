package com.liuty.maven.config;

import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Configuration 配置类
 * 系统启动初始化MyBatis映射文件对应的接口信息，通过MapperScannerConfigurer自动扫描路径加载多个接口类
 * MapperScannerConfigurer类实现了BeanDefinitionRegistryPostProcessor接口，优先其他配置类初始化，
 * 因此提取出来单独初始化
 */
@Configuration
public class MyBatisConfig {
    private static final Logger logger = LoggerFactory.getLogger(MyBatisConfig.class);
    public MyBatisConfig() {
        logger.info("MyBatisConfig启动......");
    }

    /**
     * 注册Mapper Bean自动扫描DAO映射接口类（可使用注解@Mapper形式替代）
     * 接口类跟mapper映射配合文件对应
     */
    @Bean
    public MapperScannerConfigurer getMapperScannerConfigurer() {
        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
        mapperScannerConfigurer.setBasePackage("com.liuty.maven.dao");
        return mapperScannerConfigurer;
    }
}
