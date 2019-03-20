package com.liuty.maven.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.annotation.Order;

/**
 * 类注解说明：
 *      1、注解@Configuration：标注此类是配置类
 *      2、注解@ImportResource：引入外部配置文件
 *      3、注解@Order(50)：存在多配置类的时候，可以使用此注解进行配置类排序加载顺序
 *
 * @Description: 系统启动初始化持久层配置
 *
 * 初始化实体：
 *      1、DataSource
 *      2、SqlSessionFactory
 *      3、SqlSessionTemplate
 *      4、ConnectionFilter
 */
@Configuration
@ImportResource("classpath:/spring/spring.xml")
@Order(50)
public class PropertiesConfiguration {
    private static final Logger logger = LoggerFactory.getLogger(PropertiesConfiguration.class);

    public PropertiesConfiguration() {
        logger.info("PropertiesConfiguration初始化......");
    }
}
