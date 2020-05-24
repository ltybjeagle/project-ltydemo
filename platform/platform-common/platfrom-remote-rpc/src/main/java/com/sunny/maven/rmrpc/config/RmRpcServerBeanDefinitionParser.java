package com.sunny.maven.rmrpc.config;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/4/12 20:20
 * @description：RmRpc服务端定义解析
 * @modified By：
 * @version: 1.0.0
 */
public class RmRpcServerBeanDefinitionParser extends RmRpcBaseBeanDefinitionParser {

    protected void doParse(Element element, BeanDefinitionBuilder bean) {
        String interfaceClass = element.getAttribute("interface");
        if (StringUtils.hasText(interfaceClass)) {
            bean.addPropertyValue("interfaceClass", interfaceClass);
        }
        String initBeanId = element.getAttribute("ref");
        if (StringUtils.hasText(initBeanId)) {
            bean.addPropertyValue("initBeanId", initBeanId);
        }
    }

    protected Class getBeanClass(Element element) {
        return ServerBean.class;
    }
}
