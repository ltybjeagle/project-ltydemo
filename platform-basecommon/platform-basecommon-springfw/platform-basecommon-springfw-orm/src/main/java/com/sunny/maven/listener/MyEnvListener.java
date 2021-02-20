package com.sunny.maven.listener;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * @author ：SUNNY
 * @date ：Created in 2021/1/5 15:33
 * @description：Environment监听
 * @modified By：
 * @version: 1.0.0
 */
public class MyEnvListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        System.out.println("#####MyEnvListener found!#####");
    }
}
