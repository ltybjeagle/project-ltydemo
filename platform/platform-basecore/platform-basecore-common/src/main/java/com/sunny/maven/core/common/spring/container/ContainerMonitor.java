package com.sunny.maven.core.common.spring.container;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: spring容器初始化/关闭扩展
 * @create: 2023-02-15 22:22
 */
@Component
public class ContainerMonitor implements InitializingBean, DisposableBean {
    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean afterPropertiesSet");
    }
}
