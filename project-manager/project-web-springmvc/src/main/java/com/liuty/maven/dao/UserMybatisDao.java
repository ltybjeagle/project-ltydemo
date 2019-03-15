package com.liuty.maven.dao;

import com.liuty.maven.entity.UserEntity;

/**
 * Mybatis接口文件
 * 接口全路径对应映射文件的命名空间，在使用接口方法确认映射方法执行
 *
 * @Description: Mybatis用户接口组件
 */
public interface UserMybatisDao {

    /**
     * 根据用户主键ID查询用户实例
     * @param id 用户主键ID
     * @return
     */
    UserEntity findUserById(String id);
}
