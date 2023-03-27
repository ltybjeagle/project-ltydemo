package com.sunny.maven.springframework.context;

import com.sunny.maven.springframework.beans.factory.ListableBeanFactory;

/**
 * @author SUNNY
 * @description: 应用上下文接口 Central interface to provide configuration for an application.
 * This is read-only while the application is running, but may be
 * reloaded if the implementation supports this.
 * @create: 2023-03-26 21:50
 */
public interface ApplicationContext extends ListableBeanFactory {
}
