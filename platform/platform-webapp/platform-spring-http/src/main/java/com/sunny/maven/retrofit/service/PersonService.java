package com.sunny.maven.retrofit.service;

import com.sunny.maven.retrofit.clientapi.HttpApi;
import com.sunny.maven.retrofit.dto.Person;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/8/23 17:12
 * @description：业务处理类
 * @modified By：
 * @version: 1.0.0
 */
@Service
public class PersonService {

    @Resource(type = HttpApi.class)
    private HttpApi httpApi;

    public void test() {
        httpApi.getPerson(1L);
        httpApi.savePerson(new Person());
        httpApi.getPersonCall(1L);
        httpApi.getPersonCompletableFuture(1L);
        httpApi.getPersonResponse(1L);
        httpApi.getPersonVoid(1L);
    }
}
