package com.sunny.maven.springframework.beans.factory;

/**
 * @author SUNNY
 * @description: Interface to be implemented by beans that want to release resources
 * on destruction. A BeanFactory is supposed to invoke the destroy method if it disposes a cached singleton.
 * An application context is supposed to dispose all of its singletons on close.
 * @create: 2023-05-14 22:22
 */
public interface DisposableBean {

    void destroy() throws Exception;
}
