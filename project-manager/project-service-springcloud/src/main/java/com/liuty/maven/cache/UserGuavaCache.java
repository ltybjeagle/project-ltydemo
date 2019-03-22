package com.liuty.maven.cache;

import com.liuty.maven.util.bean.BeanCopyUtil;
import com.liuty.maven.dao.jpa.UserRepository;
import com.liuty.maven.dto.CauserDTO;
import com.liuty.maven.entity.UserEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class UserGuavaCache extends SuperBaseGuavaCache<String, UserEntity> {

    @Autowired
    private UserRepository demoUserRepository;
    private BeanCopyUtil<CauserDTO, UserEntity> bcu = new BeanCopyUtil<>(CauserDTO.class, UserEntity.class);

    /**
     * 返回加载到内存中的数据，从数据库里加载
     * @param key
     * @return
     */
    @Override
    UserEntity getLoadData(String key) {
        int sleepTime = new Random().nextInt(3000);
        try {
            Thread.sleep(sleepTime);
        } catch (InterruptedException e) {
        }
        UserEntity userEntity = new UserEntity();
        CauserDTO user = demoUserRepository.getOne(Integer.valueOf(key));
        if (user != null) {
            userEntity = bcu.getCopyObject(user, userEntity);
        }
        return userEntity;
    }

    /**
     * 调用getLoadData返回null值时自定义加载到内存的值
     * @param key
     * @return
     */
    @Override
    UserEntity getLoadDataIfNull(String key) {
        UserEntity userEntity = new UserEntity();
        return userEntity;
    }
}
