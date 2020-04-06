package com.sunny.maven.facade;

import com.sunny.maven.entity.Person;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/4/5 17:23
 * @description：测试接口
 * @modified By：
 * @version: 1.0.0
 */
public interface PersonService {

    Person getInfo();
    boolean printInfo(Person person);
}
