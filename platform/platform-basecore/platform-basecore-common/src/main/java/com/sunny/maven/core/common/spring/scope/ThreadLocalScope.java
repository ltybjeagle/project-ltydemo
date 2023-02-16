package com.sunny.maven.core.common.spring.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.config.Scope;

/**
 * @author SUNNY
 * @description: spring（scope）扩展点：同一个线程中从spring容器获取到的bean都是同一个对象
 * @create: 2023-02-15 16:35
 */
public class ThreadLocalScope implements Scope {
    private static final ThreadLocal<Object> THREAD_LOCAL_SCOPE = new ThreadLocal<>();
    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Object object = THREAD_LOCAL_SCOPE.get();
        if (object != null) {
            return object;
        }
        object = objectFactory.getObject();
        THREAD_LOCAL_SCOPE.set(object);
        return object;
    }

    @Override
    public Object remove(String name) {
        THREAD_LOCAL_SCOPE.remove();
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable runnable) {
    }

    @Override
    public Object resolveContextualObject(String name) {
        return null;
    }

    @Override
    public String getConversationId() {
        return null;
    }
}
