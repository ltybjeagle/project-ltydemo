package com.liuty.maven.dao.filter;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import com.alibaba.druid.proxy.jdbc.StatementProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * 持久层过滤器：
 *      1、连接获取
 *      2、语句执行
 */
public class ConnectionFilter extends FilterEventAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionFilter.class);

    /**
     * 获取连接前
     * @param chain
     * @param info
     */
    @Override
    public void connection_connectBefore(FilterChain chain, Properties info) {
        logger.info("Thread[{}] get conn start", Thread.currentThread().getName());
    }

    /**
     * 获取连接后
     * @param connection
     */
    @Override
    public void connection_connectAfter(ConnectionProxy connection) {
        logger.info("Thread[{}] get conn end, conn: {}", Thread.currentThread().getName()
                , connection.getRawObject());
    }

    /**
     * 语句执行前
     * @param statement
     * @param sql
     */
    @Override
    protected void statementExecuteBefore(StatementProxy statement, String sql) {
        try {
            logger.info("Thread[{}] exec statement start, conn: {}, sql: {}", Thread.currentThread().getName()
                    , statement.getConnection(), sql);
        } catch (Exception ex) {
            logger.info("Thread[{}] exec statement start, sql: {}", Thread.currentThread().getName(), sql);
        }
    }

    /**
     * 语句执行后
     * @param statement
     * @param sql
     * @param result
     */
    @Override
    protected void statementExecuteAfter(StatementProxy statement, String sql, boolean result) {
        try {
            logger.info("Thread[{}] exec statement end, conn: {}, sql: {}, result: {}"
                    , Thread.currentThread().getName(), statement.getConnection(), sql, result);
        } catch (Exception ex) {
            logger.info("Thread[{}] exec statement end, sql: {}, result: {}"
                    , Thread.currentThread().getName(), sql, result);
        }
    }
}
