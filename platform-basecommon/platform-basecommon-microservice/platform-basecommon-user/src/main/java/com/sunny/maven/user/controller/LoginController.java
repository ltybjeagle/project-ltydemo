package com.sunny.maven.user.controller;

import com.sunny.maven.core.common.resp.R;
import com.sunny.maven.user.entity.Role;
import com.sunny.maven.user.entity.User;
import com.sunny.maven.user.mapper.RoleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author SUNNY
 * @description: 登录
 * @create: 2022-09-08 11:23
 */
@Slf4j
@RestController
public class LoginController {

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 登录
     * @param userId 用户名
     * @return R<String>
     * @throws AuthenticationException
     */
    @RequestMapping(value = "/queryuser", method = RequestMethod.GET)
    public R<List<Role>> login(String userId) throws Exception {
        List<Role> user = roleMapper.selectRolesByUserId(userId);
        return R.ok().data(user);
    }
}
