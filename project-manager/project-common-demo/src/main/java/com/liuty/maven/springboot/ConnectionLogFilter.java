package com.liuty.maven.springboot;

import com.alibaba.druid.filter.FilterChain;
import com.alibaba.druid.filter.FilterEventAdapter;
import com.alibaba.druid.proxy.jdbc.ConnectionProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ConnectionLogFilter extends FilterEventAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionLogFilter.class);
    /**
     * 获取连接前
     * @param chain
     * @param info
     */
    @Override
    public void connection_connectBefore(FilterChain chain, Properties info) {
        logger.info("Before Connection");
    }

    /**
     * 获取连接后
     * @param connection
     */
    @Override
    public void connection_connectAfter(ConnectionProxy connection) {
        logger.info("After Connection");
    }
}
