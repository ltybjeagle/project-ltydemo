package com.liuty.maven.command;

import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @描述: spring boot启动后调用
 * 配置：\resources\META-INF\spring.factories
 * org.springframework.boot.SpringApplicationRunListener=\
 *   com.liuty.maven.command.DemoSpringApplicationRunListener
 *
 * @创建人: Sunny
 * @创建时间: 2019/6/24
 */
public class DemoSpringApplicationRunListener implements SpringApplicationRunListener {
    @Override
    public void starting() {

    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {

    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {

    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {

    }

    @Override
    public void started(ConfigurableApplicationContext context) {

    }

    @Override
    public void running(ConfigurableApplicationContext context) {

    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {

    }
}
