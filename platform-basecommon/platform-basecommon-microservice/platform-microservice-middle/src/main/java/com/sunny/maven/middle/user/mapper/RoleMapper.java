package com.sunny.maven.middle.user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sunny.maven.middle.user.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author SUNNY
 * @description: 角色Mapper
 * @create: 2022-09-08 16:02
 */
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 根据用户查询角色列表
     * @param userId 用户ID
     * @return List<Role>
     */
    List<Role> selectRolesByUserId(@Param("userId") String userId);
}
