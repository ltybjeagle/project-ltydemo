package com.liuty.maven.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.liuty.maven.dao.filter.ConnectionFilter;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @Configuration 配置类
 * @PropertySource 加载properties配置文件
 * @Order(5) 存在多配置类的时候，可以使用此注解进行配置类排序加载顺序
 * 系统启动初始化持久层配置
 */
@Configuration
@PropertySource("classpath:jdbc.properties")
@Order(5)
public class DataSourceConfig {
    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
    public DataSourceConfig() {
        logger.info("DataSourceConfig启动......");
    }

    /**
     * Mybatis全局配置信息配置文件
     */
    private String MYBATIS_CONFIG = "mybatis/mybatis-config.xml";
    /**
     * 设置类型别名扫描包，类型别名是为Java类型命名一个短的名字
     */
    private String TYPE_ALIAS_PACKAGE = "com.liuty.maven.entity";

    /**
     * 初始化Mybatis SqlSessionFactory
     * 初始化加载项：
     * 1、Mybatis全局配置信息；;
     * 2、注入数据源bean;
     * 3、类型别名包路径（自动扫描加载路径下的实体类）;
     * 4、加载持久层接口的映射配置文件
     * @param dataSource
     * @return
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory getSqlSessionFactory(DataSource dataSource) {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setConfigLocation(new ClassPathResource(MYBATIS_CONFIG));
        sqlSessionFactoryBean.setDataSource(dataSource);
        sqlSessionFactoryBean.setTypeAliasesPackage(TYPE_ALIAS_PACKAGE);
        try {
            PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
            sqlSessionFactoryBean.setMapperLocations(
                    resolver.getResources("classpath*:com/liuty/maven/dao/mapper/*.xml"));
            return sqlSessionFactoryBean.getObject();
        } catch (Exception e) {
            logger.error("MyBatis实例化SqlSessionFactory失败，异常：{}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    /**
     * 初始化Mybatis SqlSessionTemplate，使用SqlSessionFactory生成SQL Session模板类
     * @param sqlSessionFactory
     * @return
     */
    @Bean
    public SqlSessionTemplate getSqlSessionTemplate(SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public ConnectionFilter getConnectionFilter() {
        return new ConnectionFilter();
    }

    /**
     * 使用Druid初始化数据源（连接池化）
     * 1、设置setFilters(),可以对数据源、连接添加过滤器。
     *
     * 多数据源情况：可以使用注解@Primary标注优先使用
     * @return
     */
    @Bean(name = "dataSource")
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driverClassName);
        dataSource.setInitialSize(initialSize);
        dataSource.setMinIdle(minIdle);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        dataSource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        dataSource.setValidationQuery(validationQuery);
        dataSource.setTestWhileIdle(testWhileIdle);
        dataSource.setTestOnBorrow(testOnBorrow);
        dataSource.setTestOnReturn(testOnReturn);
        dataSource.setPoolPreparedStatements(poolPreparedStatements);
        if (poolPreparedStatements) {
            dataSource.setMaxPoolPreparedStatementPerConnectionSize(
                    maxPoolPreparedStatementPerConnectionSize);
        }
        try {
            dataSource.setFilters(filters);
        } catch (SQLException e) {
            logger.error("数据源注册DruidFilters过滤器失败，异常：{}", e.getMessage());
        }
        if (connectionProperties != null && !"".equals(connectionProperties)) {
            Properties properties = new Properties();
            String[] connectionPropertiesList = connectionProperties.split(";");
            for (String connectionPropertiesMap : connectionPropertiesList) {
                String[] connectionProperty = connectionPropertiesMap.split("=");
                properties.put(connectionProperty[0], connectionProperty[1]);
            }
            dataSource.setConnectProperties(properties);
        }
        logger.info("创建dataSource[{}]", dataSource);
        return dataSource;
    }

    @Value("${spring.datasource.url}")
    private String dbUrl;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driverClassName}")
    private String driverClassName;
    @Value("${spring.datasource.initialSize}")
    private int initialSize;
    @Value("${spring.datasource.minIdle}")
    private int minIdle;
    @Value("${spring.datasource.maxActive}")
    private int maxActive;
    @Value("${spring.datasource.maxWait}")
    private int maxWait;
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;
    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;
    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;
    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;
    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    @Value("${spring.datasource.filters}")
    private String filters;
    @Value("${spring.datasource.connectionProperties}")
    private String connectionProperties;
}
