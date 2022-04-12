package com.sunny.maven.usercenter.security.mapper;

import com.sunny.maven.security.entity.Oauth2UserDetails;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: 用户信息
 * @create: 2022-02-08 15:05
 */
@Component
public interface Oauth2UserMapper {
    /**
     * 根据登录名查询用户信息
     * @param loginCode the login code
     * @return Oauth2UserDetails
     */
    @Select("select guid, name, code, password, status from fasp_t_causer t where code = #{loginCode}")
    Oauth2UserDetails findByLoginCode(@Param("loginCode") String loginCode);
}
