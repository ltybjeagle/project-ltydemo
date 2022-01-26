package com.sunny.maven.core.configuration;

import com.sunny.maven.core.datasource.DynamicDataSourceRegister;
import com.sunny.maven.core.datasource.JdbcDatasource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import javax.sql.DataSource;

/**
 * @author SUNNY
 * @description: 数据源配置
 * @create: 2022-01-13 23:28
 */
@Configuration
@Import(DynamicDataSourceRegister.class)
public class MultiDataSourceConfiguration {
    /**
     * 注册自定义数据源
     * @return
     */
    @Bean("jdbcDatasource")
    public JdbcDatasource getJdbcDatasource(@Qualifier("datasource") DataSource dataSource) {
        JdbcDatasource jdbcDatasource = new JdbcDatasource();
        jdbcDatasource.setDataSource(dataSource);
        return jdbcDatasource;
    }
}
