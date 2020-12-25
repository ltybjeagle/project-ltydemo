package com.sunny.maven.rmrpc.config;

import org.springframework.beans.factory.xml.AbstractSimpleBeanDefinitionParser;
import org.w3c.dom.Element;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/4/12 20:25
 * @description：RmRpcBaseBean基础
 * @modified By：
 * @version: 1.0.0
 */
public class RmRpcBaseBeanDefinitionParser extends AbstractSimpleBeanDefinitionParser {
    @Override
    protected String getBeanClassName(Element element) {
        return getBeanClass(element).getName();
    }
}
