package com.sunny.maven.core.datasource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * @author SUNNY
 * @description: 数据源切换
 * @create: 2022-01-13 16:05
 */
public final class DynamicDataSourceContextHolder {
    private static final Logger logger = LoggerFactory.getLogger(DynamicDataSourceContextHolder.class);
    /**
     * 存储已经注册的数据源的key
     */
    public static Set<Object> dataSourceIds = new HashSet<>();
    /**
     * 线程级别的私有变量
     */
    private static final ThreadLocal<Object> CONTEXT_HOLDER = new ThreadLocal<>();

    /**
     * 设置、切换数据源
     * @param dataSourceType 数据源类型
     */
    public static void setDataSourceType(Object dataSourceType) {
        logger.info("切换至{}数据源", dataSourceType);
        CONTEXT_HOLDER.set(dataSourceType);
    }

    /**
     * 获取数据源
     * @return Object
     */
    public static Object getDataSourceType() {
        return CONTEXT_HOLDER.get();
    }

    /**
     * 设置数据源之前一定要先移除
     */
    public static void clearDataSourceType() {
        CONTEXT_HOLDER.remove();
    }

    /**
     * 判断指定DataSource当前是否存在
     * @param dataSourceId
     * @return
     */
    public static boolean containsDataSource(Object dataSourceId){
        return dataSourceIds.contains(dataSourceId);
    }

    /**
     * 添加数据源key
     * @param keySet
     */
    public static void addDatasource(Set<Object> keySet) {
        dataSourceIds.addAll(keySet);
    }
}
