package com.liuty.maven.springboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@SpringBootApplication
@RestController
public class SpBootApplication implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SpBootApplication.class);

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public static void main(String ...args) {
        SpringApplication.run(SpBootApplication.class, args);
    }

    @RequestMapping("/hello")
    public String hello() {
        return "hello spring";
    }

    @Override
    public void run(String... args) throws Exception {
        showConnection();
        showData();
    }

    private void showConnection() throws SQLException {
        logger.info(dataSource.toString());
        Connection conn = dataSource.getConnection();
        logger.info(conn.toString());
        conn.close();
    }

    private void showData() {
        jdbcTemplate.queryForList("select * from foo")
                .forEach(row -> logger.info(row.toString()));
    }
}
