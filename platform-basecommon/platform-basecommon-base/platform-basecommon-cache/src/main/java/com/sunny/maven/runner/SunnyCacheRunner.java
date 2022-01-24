package com.sunny.maven.runner;

import com.sunny.maven.configuration.AbstractSunnyCacheAware;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * @author SUNNY
 * @description: 缓存初始化
 * @create: 2022-01-19 18:28
 */
public class SunnyCacheRunner implements ApplicationContextAware, ApplicationRunner {
    private static ApplicationContext applicationContext;
    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, AbstractSunnyCacheAware> map = applicationContext.getBeansOfType(AbstractSunnyCacheAware.class);
        map.values().forEach(cacheObj -> cacheObj.init());
    }

    /**
     * 设置应用上下文
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SunnyCacheRunner.applicationContext = applicationContext;
    }
}
