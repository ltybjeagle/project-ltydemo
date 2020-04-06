package com.sunny.maven;

import com.sunny.maven.entity.Person;
import com.sunny.maven.facade.PersonService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/4/5 17:40
 * @description：服务消费端
 * @modified By：
 * @version: 1.0.0
 */
public class RpcClient {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        PersonService personService = (PersonService) context.getBean("personService");
        System.out.println(personService.getInfo());

        Person person = new Person();
        person.setAge(23);
        person.setName("sunny");
        person.setSex("男");
        System.out.println(personService.printInfo(person));
    }
}
