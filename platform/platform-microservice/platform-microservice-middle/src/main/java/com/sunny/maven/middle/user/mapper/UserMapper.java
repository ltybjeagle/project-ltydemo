package com.sunny.maven.middle.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunny.maven.middle.user.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * @author SUNNY
 * @description: 用户Mapper
 * @create: 2022-06-22 23:46
 */
public interface UserMapper extends BaseMapper<User> {
    /**
     * 根据编码查询用户
     * @param code 名称
     * @return User
     */
    User selectByUserCode(@Param("code") String code);
}
