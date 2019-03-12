package com.liuty.maven.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.MultipartConfigElement;

import javax.servlet.ServletRegistration.Dynamic;

/**
 * 借助servlet3.0，加载DispatcherServlet
 * 同web.xml
 */
public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {
    private static final Logger logger = LoggerFactory.getLogger(WebAppInitializer.class);
    public WebAppInitializer() {
        logger.info("应用启动......");
    }
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[] {
                RootConfig.class, MyBatisConfig.class, DataSourceConfig.class};
    }

    /**
     * 指定配置类
     * @return
     */
    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[] { WebConfig.class };
    }

    /**
     * DispatcherServlet映射到"/"
     * @return
     */
    @Override
    protected String[] getServletMappings() {
        return new String[] { "/" };
    }

    /**
     * 上传文件支持(文件最大字节、请求最大字节)
     * @param registration
     */
    @Override
    protected void customizeRegistration(Dynamic registration) {
        registration.setMultipartConfig(
                new MultipartConfigElement("/tmp/uploads",
                        2097152, 4194304, 0));
    }
}
