package com.sunny.maven.report.datasource;

import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ResultSetProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;

/**
 * @author SUNNY
 * @description: 数据源拦截器
 * @create: 2022-06-21 16:57
 */
public class DruidConnectionFilter extends FilterEventAdapter {
    private static final Logger logger = LoggerFactory.getLogger(DruidConnectionFilter.class);
    private static final ThreadLocal<StopWatch> THREAD_LOCAL_EXE_TIME = new ThreadLocal<>();

    /**
     * 操作语句执行前
     * @param statement
     * @param sql
     */
    @Override
    protected void statementExecuteBefore(StatementProxy statement, String sql) {
        logger.debug("Execute use datasource: {}", statement.getConnectionProxy().getDirectDataSource().getUrl());
        logger.debug("[ {} ] exec statement, sql: {}", Thread.currentThread().getName(), sql);
        statement.getConnectionProxy().getDirectDataSource().getUrl();
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        THREAD_LOCAL_EXE_TIME.set(stopWatch);
    }

    /**
     * 操作语句执行后
     * @param statement
     * @param sql
     * @param result
     */
    @Override
    protected void statementExecuteAfter(StatementProxy statement, String sql, boolean result) {
        StopWatch stopWatch = THREAD_LOCAL_EXE_TIME.get();
        stopWatch.stop();
        logger.debug("[ {} ] exec statement end, time: {}ms", Thread.currentThread().getName(),
                stopWatch.getTotalTimeMillis());
        THREAD_LOCAL_EXE_TIME.remove();
    }

    /**
     * 查询语句执行前
     * @param statement
     * @param sql
     */
    @Override
    protected void statementExecuteQueryBefore(StatementProxy statement, String sql) {
        logger.debug("ExecuteQuery use datasource: {}", statement.getConnectionProxy().getDirectDataSource().getUrl());
        logger.debug("[ {} ] exec statement, sql: {}", Thread.currentThread().getName(), sql);
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        THREAD_LOCAL_EXE_TIME.set(stopWatch);
    }

    /**
     * 语句执行后
     * @param statement
     * @param sql
     * @param resultSet
     */
    @Override
    protected void statementExecuteQueryAfter(StatementProxy statement, String sql, ResultSetProxy resultSet) {
        StopWatch stopWatch = THREAD_LOCAL_EXE_TIME.get();
        stopWatch.stop();
        logger.debug("[ {} ] exec statement end, time: {}ms", Thread.currentThread().getName(),
                stopWatch.getTotalTimeMillis());
        THREAD_LOCAL_EXE_TIME.remove();
    }
}
