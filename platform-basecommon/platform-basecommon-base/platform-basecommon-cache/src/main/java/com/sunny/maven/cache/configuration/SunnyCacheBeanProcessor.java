package com.sunny.maven.cache.configuration;

import com.sunny.maven.cache.service.ICacheFacadeService;
import com.sunny.maven.cache.service.redis.CacheRedisService;
import com.sunny.maven.cache.template.SunnyCacheTemplate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.lang.reflect.Method;

/**
 * @author SUNNY
 * @description: 根据缓存类型，初始化缓存对象
 * @create: 2022-01-20 15:56
 */
public class SunnyCacheBeanProcessor implements EnvironmentAware, ApplicationContextAware, BeanPostProcessor {

    /**
     * 配置上下文
     */
    private Environment evn;
    private ApplicationContext applicationContext;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof SunnyCacheAwareAbstract) {
            String isCache = evn.getProperty("sunny.cache.enabled", "false");
            if (!Boolean.parseBoolean(isCache)) {
                throw new RuntimeException(beanName + "初始化报错，缓存开关关闭，不能继承AbstractSunnyCacheAware");
            }
            try {
                SunnyCacheAwareAbstract cacheInit = (SunnyCacheAwareAbstract) bean;
                String keyName = cacheInit.getClass().getSimpleName();
                Method method = cacheInit.getClass().getSuperclass().getMethod("setSunnyCacheTemplate",
                        SunnyCacheTemplate.class);
                ICacheFacadeService redisCacheService = applicationContext.getBean(CacheRedisService.class);
                method.invoke(cacheInit, new SunnyCacheTemplate("SUNNY.CACHE." +
                        StringUtils.upperCase(keyName)+ ":", redisCacheService));
            } catch (Exception e) {
                throw new RuntimeException(beanName + "初始化缓存对象设置异常，信息：" + e.getMessage());
            }
        }
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    /**
     * EnvironmentAware接口的实现方法，通过aware的方式注入，此处是environment对象
     * @param environment
     */
    @Override
    public void setEnvironment(Environment environment) {
        this.evn = environment;
    }

    /**
     * 设置应用上下文
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
