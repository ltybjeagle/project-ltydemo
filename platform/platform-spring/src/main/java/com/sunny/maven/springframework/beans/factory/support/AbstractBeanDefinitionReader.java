package com.sunny.maven.springframework.beans.factory.support;

import com.sunny.maven.springframework.core.io.DefaultResourceLoader;
import com.sunny.maven.springframework.core.io.ResourceLoader;

/**
 * @author SUNNY
 * @description: Abstract base class for bean definition readers which implement
 * @create: 2023-03-25 13:02
 */
public abstract class AbstractBeanDefinitionReader implements BeanDefinitionReader {

    private final BeanDefinitionRegistry registry;

    private ResourceLoader resourceLoader;

    protected AbstractBeanDefinitionReader(BeanDefinitionRegistry registry) {
        this(registry, new DefaultResourceLoader());
    }

    public AbstractBeanDefinitionReader(BeanDefinitionRegistry registry, ResourceLoader resourceLoader) {
        this.registry = registry;
        this.resourceLoader = resourceLoader;
    }

    @Override
    public BeanDefinitionRegistry getRegistry(){
        return registry;
    }

    @Override
    public ResourceLoader getResourceLoader() {
        return resourceLoader;
    }
}
