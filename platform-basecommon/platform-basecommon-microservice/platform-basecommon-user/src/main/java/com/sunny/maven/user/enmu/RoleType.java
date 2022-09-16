package com.sunny.maven.user.enmu;

import lombok.Getter;

/**
 * @author SUNNY
 * @description: 角色类型
 * @create: 2022-09-08 15:38
 */
@Getter
public enum RoleType {
    /**
     * 0：管理员类型；1：业务类型
     */
    MANAGER(0),
    AGENCY(1);
    /**
     * 角色类型
     */
    private int roleType;

    RoleType(int roleType) {
        this.roleType = roleType;
    }
}
