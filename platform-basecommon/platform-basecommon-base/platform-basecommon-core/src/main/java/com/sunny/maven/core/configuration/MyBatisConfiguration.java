package com.sunny.maven.core.configuration;

import org.apache.ibatis.session.LocalCacheScope;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.sql.DataSource;

/**
 * @author SUNNY
 * @description: MyBatis配置项
 * @create: 2022-01-18 14:16
 */
@Configuration
@EnableTransactionManagement
public class MyBatisConfiguration implements TransactionManagementConfigurer {
    private DataSource dataSource;

    /**
     * 创建sqlsessionfacory
     * @param jdbcDatasource 数据源
     * @return SqlSessionFactory
     * @throws Exception 异常
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(DataSource jdbcDatasource) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(jdbcDatasource);
        org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();
        configuration.setLazyLoadingEnabled(false);
        configuration.setMultipleResultSetsEnabled(false);
        configuration.setLocalCacheScope(LocalCacheScope.SESSION);

        //把查询出来的字段转换成小写
        MapWrapperFactory mapWrapperFactory = new MapWrapperFactory();
        configuration.setObjectWrapperFactory(mapWrapperFactory);

        factoryBean.setConfiguration(configuration);
        factoryBean.setTypeAliasesPackage("com.sunny.maven");
        // 加载MyBatis配置文件
        PathMatchingResourcePatternResolver resourcePatternResolver = new PathMatchingResourcePatternResolver();
        // 能加载多个，所以可以配置通配符(如：classpath*:mapper/**/*.xml)
        factoryBean.setMapperLocations(resourcePatternResolver.
                getResources("classpath*:mapper/**/*.xml"));
        return factoryBean.getObject();
    }

    /**
     * 设置SqlSessionTemplate
     * @param sqlSessionFactory sqlsessionfacory
     * @return SqlSessionTemplate
     */
    @Bean
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    /**
     * 事务管理器
     * @return TransactionManager
     */
    @Bean
    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    /**
     * 构造函数
     * @param jdbcDatasource 数据源
     */
    @Autowired
    public MyBatisConfiguration(DataSource jdbcDatasource) {
        this.dataSource = jdbcDatasource;
    }
}
