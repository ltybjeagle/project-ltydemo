package com.sunny.maven.rpcserver;

import com.sunny.maven.entity.Person;
import com.sunny.maven.facade.PersonService;
import com.sunny.maven.rmrpc.annotation.RmRpcService;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/4/1 17:32
 * @description：服务接口实现类
 * @modified By：
 * @version: 1.0.0
 */
@RmRpcService(PersonService.class)
public class PersonServiceImpl implements PersonService {
    @Override
    public Person getInfo() {
        Person person = new Person();
        person.setAge(22);
        person.setName("sunny");
        person.setSex("男");
        return person;
    }

    @Override
    public boolean printInfo(Person person) {
        if(person != null){
            System.out.println(person);
            return true;
        }
        return false;
    }
}
