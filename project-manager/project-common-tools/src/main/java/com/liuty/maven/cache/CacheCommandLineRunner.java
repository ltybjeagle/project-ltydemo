package com.liuty.maven.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/22
 */
@Component
public class CacheCommandLineRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(CacheCommandLineRunner.class);

    @Autowired
    private ICacheBusService cacheBusService;

    @Override
    public void run(String... args) throws Exception {
        logger.info("init cache start ...");
        cacheBusService.refresh();
        logger.info("init cache end ...");
    }
}
