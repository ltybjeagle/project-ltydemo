package com.liuty.maven.rest;

import com.liuty.maven.dao.jpa.UserRepository;
import com.liuty.maven.dto.Fasp_T_Causer;
import com.liuty.maven.log.LoggerLevel;
import com.liuty.maven.log.LoggerUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRestful {

    @Autowired
    private UserRepository demoUserRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Fasp_T_Causer findById(@PathVariable String id) {
        LoggerUtil.logger(LoggerLevel.INFO, () -> "======================》服务调用");
        Fasp_T_Causer user = demoUserRepository.findOne(id);
        return user;
    }
}
