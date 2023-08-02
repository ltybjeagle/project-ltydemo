package com.sunny.maven.middle.springmvc.listener;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.servlet.annotation.WebListener;

/**
 * @author SUNNY
 * @description:
 * @create: 2023/7/21 17:33
 */
@Slf4j
@WebListener
public class MiddleListener implements ServletRequestListener {
    @Override
    public void requestDestroyed(ServletRequestEvent sre) {
        log.info("MiddleListener>>>requestDestroyed");
    }

    @Override
    public void requestInitialized(ServletRequestEvent sre) {
        log.info("MiddleListener>>>requestInitialized");
    }
}
