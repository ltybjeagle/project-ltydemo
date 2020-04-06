package com.sunny.maven.rmrpc.config;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/3/22 17:12
 * @description：RmRpcNamespaceHandler
 * @modified By：
 * @version: 1.0.0
 */
public class RmRpcNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("reference", new RmRpcBeanDefinitionParser());
    }
}
