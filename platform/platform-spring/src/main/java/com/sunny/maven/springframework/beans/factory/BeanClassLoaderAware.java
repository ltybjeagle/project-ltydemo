package com.sunny.maven.springframework.beans.factory;

/**
 * @author SUNNY
 * @description: Callback that allows a bean to be aware of the bean
 * {@link ClassLoader class loader}; that is, the class loader used by the
 * present bean factory to load bean classes.
 * @create: 2023/5/28 12:06
 */
public interface BeanClassLoaderAware extends Aware {
    void setBeanClassLoader(ClassLoader classLoader);
}
