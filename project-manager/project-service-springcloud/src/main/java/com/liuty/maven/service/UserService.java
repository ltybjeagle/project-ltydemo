package com.liuty.maven.service;

import com.liuty.maven.dao.jpa.UserRepository;
import com.liuty.maven.dto.Fasp_T_Causer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository demoUserRepository;

    /**
     * 根据ID查询用户
     * @param id
     * @return
     * @throws Exception
     */
    public Fasp_T_Causer findUserById(String id) throws Exception {
        int sleepTime = new Random().nextInt(3000);
        Thread.sleep(sleepTime);
        return demoUserRepository.findOne(id);
    }
}
