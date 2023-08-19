package com.sunny.maven.middle.authentication.security.mapper;

import com.sunny.maven.middle.authentication.security.entity.Role;
import com.sunny.maven.middle.authentication.security.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author SUNNY
 * @description:
 * @create: 2023/8/12 21:03
 */
@Mapper
public interface UserMapper {
    User loadUserByUsername(String username);
    List<Role> getUserRolesByUserId(Integer userId);
}
