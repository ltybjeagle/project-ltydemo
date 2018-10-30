package com.liuty.maven.rest;

import com.liuty.maven.dao.jpa.DemoUserRepository;
import com.liuty.maven.dto.Fasp_T_Causer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoUserRestful {

    @Autowired
    private DemoUserRepository demoUserRepository;

    @GetMapping("{id}")
    public Fasp_T_Causer findById(@PathVariable String id) {
        Fasp_T_Causer user = demoUserRepository.findOne(id);
        return user;
    }
}
