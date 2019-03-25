package com.liuty.maven;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * 类注解说明：
 *      1、注解@SpringBootApplication：标注此应用是SPRING BOOT应用
 *          exclude属性：关闭对应自动配置（此例关闭数据源、事务管理器、JDBC操作模板实例的自动实例化）
 *      2、注解@Bean：标注容器管理实例化对象
 *      3、注解@ConfigurationProperties：获取指定配置文件映射信息
 *      4、注解@Resource：根据BEAN ID注入资源
 *
 * @Description: 双数据源应用(使用H2内存数据库)
 *
 */
//@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class
//        , DataSourceTransactionManagerAutoConfiguration.class, JdbcTemplateAutoConfiguration.class})
public class MultiDSMainApplication {
    private static final Logger logger = LoggerFactory.getLogger(MultiDSMainApplication.class);

    public static void main(String ...args) {
        SpringApplication.run(MultiDSMainApplication.class, args);
    }

    /**
     * 加载foo.datasource配置信息生成属性配置对象
     *
     * @return
     */
    @Bean
    @ConfigurationProperties("foo.datasource")
    public DataSourceProperties getFooDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 加载bar.datasource配置信息生成属性配置对象
     *
     * @return
     */
    @Bean
    @ConfigurationProperties("bar.datasource")
    public DataSourceProperties getBarDataSourceProperties() {
        return new DataSourceProperties();
    }

    /**
     * 使用foo.datasource配置对象生成数据源
     *
     * @return
     */
    @Bean(name = "fooDataSource")
    public DataSource getFooDataSource() {
        DataSourceProperties dataSourceProperties = getFooDataSourceProperties();
        logger.info("load foo datasource: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    /**
     * 使用bar.datasource配置对象生成数据源
     *
     * @return
     */
    @Bean(name = "barDataSource")
    public DataSource getBarDataSource() {
        DataSourceProperties dataSourceProperties = getBarDataSourceProperties();
        logger.info("load bar datasource: {}", dataSourceProperties.getUrl());
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    /**
     * 生成事务管理器，注入foo数据源
     *
     * @param fooDataSource
     * @return
     */
    @Bean
    @Resource
    public PlatformTransactionManager getFooTxManager(DataSource fooDataSource) {
        return new DataSourceTransactionManager(fooDataSource);
    }

    /**
     * 生成事务管理器，注入bar数据源
     *
     * @param barDataSource
     * @return
     */
    @Bean
    @Resource
    public PlatformTransactionManager getBarTxManager(DataSource barDataSource) {
        return new DataSourceTransactionManager(barDataSource);
    }

    @Bean(name = "jdbcTemplate")
    public JdbcTemplate getJdbcTemplate(DataSource fooDataSource) {
        return new JdbcTemplate(fooDataSource);
    }

}
