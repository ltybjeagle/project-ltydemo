package com.liuty.maven.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/22
 */
@Component
@Scope(value = "singleton")
public class CacheBusServiceImpl implements ApplicationContextAware, ICacheBusService {

    private static final Logger logger = LoggerFactory.getLogger(CacheBusServiceImpl.class);
    private static final String DEFAULT_CACHE_FACADE = "com.liuty.maven.cache.mycache.MyCacheService";
    private static final Lock lock = new ReentrantLock();
    private static ApplicationContext applicationContext;
    @Value("cacheFacade.instance")
    private String cacheFacade;

    @Override
    public void refresh() {
        try {
            lock.lock();
            if (cacheFacade == null || "".equalsIgnoreCase(cacheFacade)) {
                cacheFacade = DEFAULT_CACHE_FACADE;
            }
            Class cacheClass = null;
            try {
                try {
                    cacheClass = Class.forName(cacheFacade);
                } catch (ClassNotFoundException e) {
                    cacheClass = Class.forName(DEFAULT_CACHE_FACADE);
                }
                cacheClass = Class.forName(cacheFacade);
                Constructor constructor = cacheClass.getConstructor(Class.class, Class.class);
                Map<String, ICacheInit> cacheMap = applicationContext.getBeansOfType(ICacheInit.class);
                cacheMap.values().stream().forEach(cacheObj -> {
                    Class keyClazz = cacheObj.getKeyClass() == null ? String.class : cacheObj.getKeyClass();
                    Class ValueClazz = cacheObj.getValueClass() == null ? String.class : cacheObj.getValueClass();
                    ICacheService cacheFacadeService = null;
                    try {
                        cacheFacadeService = (ICacheService) constructor.newInstance(keyClazz, ValueClazz);
                    } catch (Exception e) {
                        logger.error("缓存刷新异常：{}", e);
                    }
                    cacheObj.setCacheService(cacheFacadeService);
                    cacheObj.initCache();
                });
            } catch (Exception ex) {
                logger.error("缓存刷新异常：{}", ex);
            }
        } finally {
            lock.unlock();
        }
    }

    /**
     * 设置应用上下文
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CacheBusServiceImpl.applicationContext = applicationContext;
    }
}
