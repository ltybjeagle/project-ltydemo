package com.sunny.maven.core.extend;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * @author SUNNY
 * @description: ExtApplicationContextInitializer
 *               容器刷新之前调用initialize方法，激活一些配置，或者利用这时候class还没被类加载器加载的时机，
 *               进行动态字节码注入等操作
 * @create: 2022-10-17 23:13
 */
@Slf4j
public class ExtApplicationContextInitializer implements ApplicationContextInitializer {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        log.debug("1.[ApplicationContextInitializer] --> initialize");
    }
}
