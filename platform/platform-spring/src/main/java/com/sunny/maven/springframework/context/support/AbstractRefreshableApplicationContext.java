package com.sunny.maven.springframework.context.support;

import com.sunny.maven.springframework.beans.BeansException;
import com.sunny.maven.springframework.beans.factory.ConfigurableListableBeanFactory;
import com.sunny.maven.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author SUNNY
 * @description: 抽象基类刷新应用上下文 Base class for {@link com.sunny.maven.springframework.context.ApplicationContext}
 * implementations which are supposed to support multiple calls to {@link #refresh()},
 * creating a new internal bean factory instance every time.
 * Typically (but not necessarily), such a context will be driven by
 * a set of config locations to load bean definitions from.
 * @create: 2023-03-26 22:44
 */
public abstract class AbstractRefreshableApplicationContext extends AbstractApplicationContext {

    private DefaultListableBeanFactory beanFactory;

    @Override
    protected void refreshBeanFactory() throws BeansException {
        DefaultListableBeanFactory beanFactory = createBeanFactory();
        loadBeanDefinitions(beanFactory);
        this.beanFactory = beanFactory;
    }

    private DefaultListableBeanFactory createBeanFactory() {
        return new DefaultListableBeanFactory();
    }

    protected abstract void loadBeanDefinitions(DefaultListableBeanFactory beanFactory);

    @Override
    protected ConfigurableListableBeanFactory getBeanFactory() {
        return beanFactory;
    }
}
