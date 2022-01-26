package com.sunny.maven.core.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Map;

/**
 * @author SUNNY
 * @description: 数据源路由
 * @create: 2022-01-13 15:28
 */
public class DynamicDataSourceRouter extends AbstractRoutingDataSource {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceRouter.class);

    @Override
    protected Object determineCurrentLookupKey() {
        Object dataSourceName = DynamicDataSourceContextHolder.getDataSourceType();
        if (!DynamicDataSourceContextHolder.containsDataSource(dataSourceName)) {
            logger.info("can not found datasource by key: {},this session may use default datasource"
                    , dataSourceName);
        }
        if (dataSourceName == null) {
            logger.info("当前数据源是：{}", "默认数据源");
        } else {
            logger.info("当前数据源是：{}", dataSourceName);
        }
        return dataSourceName;
    }

    /**
     * 在获取key的集合，目的只是为了添加一些告警日志
     */
    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        try {
            Field sourceMapField = AbstractRoutingDataSource.class.getDeclaredField("resolvedDataSources");
            sourceMapField.setAccessible(true);
            Map<Object, DataSource> sourceMap = (Map<Object, DataSource>) sourceMapField.get(this);
            DynamicDataSourceContextHolder.addDatasource(sourceMap.keySet());
            sourceMapField.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
