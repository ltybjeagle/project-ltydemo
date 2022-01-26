package com.sunny.maven.core.datasource;

import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import com.sunny.maven.core.util.ThreadLocalMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 * @author SUNNY
 * @description: 数据源拦截器
 * @create: 2022-01-13 15:17
 */
public class DruidConnectionFilter extends FilterEventAdapter {
    private static final Logger logger = LoggerFactory.getLogger(DruidConnectionFilter.class);
    public static final String DRUID_TIME = "DRUID_TIME";
    /**
     * 操作语句执行前
     * @param statement
     * @param sql
     */
    @Override
    protected void statementExecuteBefore(StatementProxy statement, String sql) {
        logger.info("[ {} ] exec statement, sql: {}", Thread.currentThread().getName(), sql);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ThreadLocalMap.put(DRUID_TIME, stopWatch);
    }

    /**
     * 操作语句执行后
     * @param statement
     * @param sql
     * @param result
     */
    @Override
    protected void statementExecuteAfter(StatementProxy statement, String sql, boolean result) {
        StopWatch stopWatch = (StopWatch) ThreadLocalMap.get(DRUID_TIME);
        stopWatch.stop();
        logger.info("[ {} ] exec statement end, time: {}ms", Thread.currentThread().getName(),
                stopWatch.getTotalTimeMillis());
        ThreadLocalMap.remove(DRUID_TIME);
    }

    /**
     * 查询语句执行前
     * @param statement
     * @param sql
     */
    @Override
    protected void statementExecuteQueryBefore(StatementProxy statement, String sql) {
        logger.info("[ {} ] exec statement, sql: {}", Thread.currentThread().getName(), sql);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        ThreadLocalMap.put(DRUID_TIME, stopWatch);
    }

    /**
     * 语句执行后
     * @param statement
     * @param sql
     * @param resultSet
     */
    @Override
    protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
        StopWatch stopWatch = (StopWatch) ThreadLocalMap.get(DRUID_TIME);
        stopWatch.stop();
        logger.info("[ {} ] exec statement end, time: {}ms", Thread.currentThread().getName(),
                stopWatch.getTotalTimeMillis());
        ThreadLocalMap.remove(DRUID_TIME);
    }
}
