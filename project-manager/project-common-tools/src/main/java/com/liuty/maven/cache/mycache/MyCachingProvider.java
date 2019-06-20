package com.liuty.maven.cache.mycache;

import javax.cache.CacheManager;
import javax.cache.configuration.OptionalFeature;
import javax.cache.spi.CachingProvider;
import java.net.URI;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @描述:
 *
 * @创建人: Sunny
 * @创建时间: 2019/4/19
 */
public class MyCachingProvider implements CachingProvider {

    private final Lock lock = new ReentrantLock();
    private static String storeType = "LRU";
    private CacheManager cacheManager;

    @Override
    public CacheManager getCacheManager() {
        if (cacheManager == null) {
            try {
                lock.lock();
                if (cacheManager == null) {
                    cacheManager = new MyCacheManager(this, storeType);
                }
            } finally {
                lock.unlock();
            }
        }
        return cacheManager;
    }

    /**
     * 获取缓存提供者(单例)
     * @return
     */
    public static MyCachingProvider getInstance() {
        return CachingProviderInstance.MY_CACHING_PROVIDER;
    }

    private MyCachingProvider() {}

    private static class CachingProviderInstance {
        private static final MyCachingProvider MY_CACHING_PROVIDER = new MyCachingProvider();
    }

    /**
     * 待扩展
     * @param uri
     * @param classLoader
     * @param properties
     * @return
     */
    @Override
    public CacheManager getCacheManager(URI uri, ClassLoader classLoader, Properties properties) {
        return null;
    }

    /**
     * 待扩展
     * @return
     */
    @Override
    public ClassLoader getDefaultClassLoader() {
        return null;
    }

    /**
     * 待扩展
     * @return
     */
    @Override
    public URI getDefaultURI() {
        return null;
    }

    /**
     * 待扩展
     * @return
     */
    @Override
    public Properties getDefaultProperties() {
        return null;
    }

    /**
     * 待扩展
     * @param uri
     * @param classLoader
     * @return
     */
    @Override
    public CacheManager getCacheManager(URI uri, ClassLoader classLoader) {
        return null;
    }

    /**
     * 待扩展
     */
    @Override
    public void close() {

    }

    /**
     * 待扩展
     * @param classLoader
     */
    @Override
    public void close(ClassLoader classLoader) {

    }

    /**
     * 待扩展
     * @param uri
     * @param classLoader
     */
    @Override
    public void close(URI uri, ClassLoader classLoader) {

    }

    /**
     * 待扩展
     * @param optionalFeature
     * @return
     */
    @Override
    public boolean isSupported(OptionalFeature optionalFeature) {
        return false;
    }
}
