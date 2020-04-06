package com.sunny.maven.rmrpc.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/3/22 17:01
 * @description：RmRpcBeanDefinitionParser解析
 * @modified By：
 * @version: 1.0.0
 */
public class RmRpcBeanDefinitionParser extends AbstractSingleBeanDefinitionParser {

    protected void doParse(Element element, BeanDefinitionBuilder bean) {
        String interfaceClass = element.getAttribute("interface");
        if (StringUtils.hasText(interfaceClass)) {
            bean.addPropertyValue("interfaceClass", interfaceClass);
        }
    }

    protected Class getBeanClass(Element element) {
        return ReferenceBean.class;
    }
}
