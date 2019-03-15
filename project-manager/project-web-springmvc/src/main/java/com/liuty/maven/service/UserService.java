package com.liuty.maven.service;

import com.liuty.maven.dao.UserMybatisDao;
import com.liuty.maven.entity.UserEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @Service 标注实例由SPRING容器加载管理
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
     * @Transactional 标注方法开启事务
     * 根据用户主键ID查询用户实例
     *
     * @param id 用户主键ID
     * @return
     */
    @Transactional
    public UserEntity findUserById(String id) {
        return userMybatisDao.findUserById(id);
    }
}
