package com.liuty.maven.command;

import com.liuty.maven.config.MyProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * 类注解说明：
 *      1、注解@Component：标注容器管理实例化对象
 *
 * @Description: 自定义启动时执行命令
 *
 */
@Component
@Order(5)
public class ShowCommandLineRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(ShowCommandLineRunner.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        showConnection();
        showData();
    }

    /**
     * 数据源、连接信息
     * @throws SQLException
     */
    private void showConnection() throws SQLException {
        logger.info(dataSource.toString());
        Connection conn = dataSource.getConnection();
        logger.info(conn.toString());
        conn.close();
    }

    /**
     * 验证jdbcTemplate
     */
    private void showData() {
        logger.info(jdbcTemplate.toString());
        jdbcTemplate.queryForList("select * from t_causer")
                .forEach(row -> logger.info(row.toString()));
    }
}
