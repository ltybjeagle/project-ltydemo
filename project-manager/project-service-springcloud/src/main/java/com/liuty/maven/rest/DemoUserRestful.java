package com.liuty.maven.rest;

import com.liuty.maven.dao.jpa.DemoUserRepository;
import com.liuty.maven.dto.Fasp_T_Causer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
public class DemoUserRestful {

    @Autowired
    private DemoUserRepository demoUserRepository;

    //@GetMapping("{id}")
    @RequestMapping(value = "{id}", method = RequestMethod.GET)
    public Fasp_T_Causer findById(@PathVariable String id) {
        System.out.println("======================服务端口：8000");
        Fasp_T_Causer user = demoUserRepository.findOne(id);
        return user;
    }
}
