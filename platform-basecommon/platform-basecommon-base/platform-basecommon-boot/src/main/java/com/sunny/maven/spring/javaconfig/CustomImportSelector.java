package com.sunny.maven.spring.javaconfig;

import com.sunny.maven.spring.xmlbean.PersonService;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author SUNNY
 * @description: 自定义BEAN注册——01
 * @create: 2021-07-29 18:58
 */
public class CustomImportSelector implements ImportBeanDefinitionRegistrar {

    /**
     * 注册BEAN
     * @param importingClassMetadata 元信息
     * @param registry 注册器
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        // 定义BEAN信息
        RootBeanDefinition personBeanDefinition = new RootBeanDefinition(PersonService.class);
        personBeanDefinition.setScope(ConfigurableBeanFactory.SCOPE_PROTOTYPE);
        registry.registerBeanDefinition("personService", personBeanDefinition);
    }
}
