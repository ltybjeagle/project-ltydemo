package com.sunny.maven.spring;

import com.sunny.maven.spring.annotation.PersonAnnService;
import com.sunny.maven.spring.xmlbean.PersonFactoryBean;
import com.sunny.maven.spring.xmlbean.PersonService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author SUNNY
 * @description: Spring init Bean
 * @create: 2021-07-27 23:38
 */
public class SpringBeanDemo {
    public static void main(String ...args) {
        // 使用XML配置文件配置BEAN对象
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring.xml");
        PersonService personService = (PersonService) applicationContext.getBean("personService");
        System.out.println(personService);
        // 静态工厂创建BEAN
        PersonService personServiceStaticFactory =
                (PersonService) applicationContext.getBean("personService.staticFactory");
        System.out.println(personServiceStaticFactory);
        // 实例工厂创建BEAN
        PersonService personServiceInstanceFactory =
                (PersonService) applicationContext.getBean("personService.instanceFactory");
        System.out.println(personServiceInstanceFactory);
        // 实例工厂BEAN创建BEAN
        // 获取BEAN对象
        PersonService personServiceFactoryBean =
                (PersonService) applicationContext.getBean("personService.factoryBean");
        // 获取工厂BEAN对象
        PersonFactoryBean personFactoryBean =
                (PersonFactoryBean) applicationContext.getBean("&personService.factoryBean");
        System.out.println(personFactoryBean + "、" + personServiceFactoryBean);
        // 使用构造器注入属性
        PersonService personServiceConstructor =
                (PersonService) applicationContext.getBean("personService.constructor");
        System.out.println(personServiceConstructor);

        // 通过注解定义BEAN
        PersonAnnService personAnnService = (PersonAnnService) applicationContext.getBean("personAnnService");
        System.out.println(personAnnService);
    }
}
