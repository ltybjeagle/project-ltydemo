package com.liuty.maven.service;

import com.liuty.maven.dao.UserMybatisDao;
import com.liuty.maven.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * 类注解说明：
 *      1、注解@Service：标注实例由SPRING容器加载管理
 *      2、注解@Transactional：标注方法开启事务，可用于类、方法
 *
 * @Description: 用户业务服务组件
 */
@Service
public class UserService {

    /**
     * 注入用户持久化组件
     */
    @Resource
    private UserMybatisDao userMybatisDao;

    /**
     * 根据用户主键ID查询用户实例
     *
     * @param id 用户主键ID
     * @return
     */
    @Transactional
    public Optional<UserEntity> findUserById(String id) {
        return Optional.ofNullable(userMybatisDao.findUserById(id));
    }

    /**
     * 根据用户名和密码查询用户实例
     *
     * @param userCode 用户编码
     * @param password 密码
     * @return
     */
    public Optional<UserEntity> findUserByNameAndPassword(String userCode, String password) {
        UserEntity userEntity = new UserEntity();
        userEntity.setCode(userCode);
        userEntity.setPassword(password);
        return Optional.ofNullable(userMybatisDao.findUserByNameAndPassword(userEntity));
    }
}
