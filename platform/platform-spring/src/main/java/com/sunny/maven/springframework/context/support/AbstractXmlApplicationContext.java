package com.sunny.maven.springframework.context.support;

import com.sunny.maven.springframework.beans.factory.support.DefaultListableBeanFactory;
import com.sunny.maven.springframework.beans.factory.xml.XmlBeanDefinitionReader;

/**
 * @author SUNNY
 * @description: 抽象基类 XML 上下文 Convenient base class for
 * {@link com.sunny.maven.springframework.context.ApplicationContext}
 * implementations, drawing configuration from XML documents containing bean definitions
 * understood by an {@link com.sunny.maven.springframework.beans.factory.xml.XmlBeanDefinitionReader}.
 * @create: 2023-03-26 22:50
 */
public abstract class AbstractXmlApplicationContext extends AbstractRefreshableApplicationContext {
    @Override
    protected void loadBeanDefinitions(DefaultListableBeanFactory beanFactory) {
        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(beanFactory, this);
        String[] configLocations =  getConfigLocations();
        if (null != configLocations){
            beanDefinitionReader.loadBeanDefinitions(configLocations);
        }
    }

    protected abstract String[] getConfigLocations();
}
