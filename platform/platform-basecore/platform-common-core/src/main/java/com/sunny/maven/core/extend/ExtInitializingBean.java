package com.sunny.maven.core.extend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author SUNNY
 * @description: ExtInitializingBean 初始化bean的时候都会执行该方法。
 *               这个扩展点的触发时机在postProcessAfterInitialization之前
 *               进行系统启动的时候一些业务指标的初始化工作
 * @create: 2022-10-18 16:10
 */
@Slf4j
public class ExtInitializingBean implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("17.[InitializingBean] afterPropertiesSet");
    }
}
