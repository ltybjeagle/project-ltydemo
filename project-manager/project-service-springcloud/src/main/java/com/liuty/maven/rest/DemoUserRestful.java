package com.liuty.maven.rest;

import com.liuty.maven.dao.jpa.DemoUserRepository;
import com.liuty.maven.dto.Fasp_T_Causer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class DemoUserRestful {

    private static final Logger logger = LoggerFactory.getLogger(DemoUserRestful.class);

    @Autowired
    private DemoUserRepository demoUserRepository;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Fasp_T_Causer findById(@PathVariable String id) {
        logger.info("======================服务端口：8000");
        Fasp_T_Causer user = demoUserRepository.findOne(id);
        return user;
    }
}
