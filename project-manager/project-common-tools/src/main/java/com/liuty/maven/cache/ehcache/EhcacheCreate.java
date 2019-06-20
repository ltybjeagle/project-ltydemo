package com.liuty.maven.cache.ehcache;

import com.liuty.maven.cache.CacheEnvProperty;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.ehcache.EhCacheManagerFactoryBean;
import org.springframework.core.io.ClassPathResource;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/22
 */
public class EhcacheCreate {

    private EhCacheCacheManager ehCacheCacheManager;
    private String DEFAULT_EHCACHE = "ehcache/default_ehcache.xml";
    private String EHCACHE_PROPERTY_PATH =
            CacheEnvProperty.getProperties("spring.ehcache.property.path");

    public static EhcacheCreate getInstance() {
        return EhcacheCreateInstance.EHCACHE_CREATE;
    }

    public EhCacheCacheManager getEhCacheCacheManager() {
        return this.ehCacheCacheManager;
    }

    private EhcacheCreate() {
        EHCACHE_PROPERTY_PATH = EHCACHE_PROPERTY_PATH == null ? DEFAULT_EHCACHE
                : EHCACHE_PROPERTY_PATH;
        EhCacheManagerFactoryBean ehCacheManagerFactoryBean = new EhCacheManagerFactoryBean();
        ehCacheManagerFactoryBean.setConfigLocation(new ClassPathResource(EHCACHE_PROPERTY_PATH));
        ehCacheManagerFactoryBean.setShared(true);
        ehCacheCacheManager = new EhCacheCacheManager(ehCacheManagerFactoryBean.getObject());
    }

    private static class EhcacheCreateInstance {
        private static final EhcacheCreate EHCACHE_CREATE = new EhcacheCreate();
    }
}
