package com.liuty.maven.service;

import com.liuty.maven.beanutil.BeanCopyUtil;
import com.liuty.maven.dao.jpa.UserRepository;
import com.liuty.maven.dto.Fasp_T_Causer;
import com.liuty.maven.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class UserService {

    @Autowired
    private UserRepository demoUserRepository;
    private BeanCopyUtil<Fasp_T_Causer, UserEntity> bcu = new BeanCopyUtil<>(Fasp_T_Causer.class, UserEntity.class);

    /**
     * 根据ID查询用户
     * @param id
     * @return
     * @throws Exception
     */
    public UserEntity findUserById(String id) throws Exception {
        int sleepTime = new Random().nextInt(3000);
        Thread.sleep(sleepTime);
        Fasp_T_Causer user = demoUserRepository.findOne(id);
        UserEntity userEntity = new UserEntity();
        userEntity = bcu.getCopyObject(user, userEntity);
        return userEntity;
    }
}
