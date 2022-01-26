package com.sunny.maven.core.datasource;

import com.sunny.maven.core.common.context.UserInfoContextHolder;
import com.sunny.maven.core.interceptor.JdbcConnectionInterceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.FactoryBean;

import javax.sql.DataSource;
import java.lang.reflect.Proxy;

/**
 * @author SUNNY
 * @description: 自定义数据源
 * @create: 2022-01-13 23:08
 */
public class JdbcDatasource implements FactoryBean<DataSource> {
    private static final Logger logger = LoggerFactory.getLogger(JdbcDatasource.class);
    private DataSource dataSource;

    /**
     * 获取当前财年的datasource
     * @return DataSource
     */
    public DataSource getCurrentYearDs() {
        Object dsk = UserInfoContextHolder.getContext().getDatasourceKey();
        if (dsk != null) {
            boolean isExist = DynamicDataSourceContextHolder.containsDataSource(dsk);
            if (isExist) {
                DynamicDataSourceContextHolder.setDataSourceType(dsk);
                logger.info("线程{}使用{}数据源", Thread.currentThread().getName(), dsk);
            } else {
                DynamicDataSourceContextHolder.clearDataSourceType();
                logger.info("没有配置数据源信息，使用默认的数据源");
            }
        } else {
            logger.info("线程{}中没有数据源信息，使用默认的数据源", Thread.currentThread().getName());
        }
        return getDataSource();
    }

    @Override
    public DataSource getObject() throws Exception {
        JdbcConnectionInterceptor dsh = new JdbcConnectionInterceptor(this);
        // 生成代理.
        Class<?>[] interfaces = new Class<?>[]{ DataSource.class };
        Object result = Proxy.newProxyInstance(JdbcDatasource.class.getClassLoader(), interfaces, dsh);
        return (DataSource) result;
    }

    @Override
    public Class<?> getObjectType() {
        return DataSource.class;
    }

    public DataSource getDataSource() {
        return dataSource;
    }

    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }
}
