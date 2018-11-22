package com.liuty.maven.rest;

import com.liuty.maven.dao.jpa.UserRepository;
import com.liuty.maven.dto.Fasp_T_Causer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Random;

@RestController
public class UserRestful {

    @Autowired
    private UserRepository demoUserRepository;

    @RequestMapping(value = "/user/findbyid/{id}", method = RequestMethod.GET)
    public Fasp_T_Causer findById(@PathVariable String id) throws Exception {
        //LoggerUtil.logger(LoggerLevel.INFO, () -> "======================》服务调用");
        System.out.println(Thread.currentThread().getName() + "======================》服务调用");
        int sleepTime = new Random().nextInt(3000);
        Thread.sleep(sleepTime);
        Fasp_T_Causer user = demoUserRepository.findOne(id);
        return user;
    }
}
