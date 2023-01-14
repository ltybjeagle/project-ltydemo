package com.sunny.maven.user.enmu;

import lombok.Getter;

/**
 * @author SUNNY
 * @description: 用户类型
 * @create: 2022-09-08 14:42
 */
@Getter
public enum UserType {
    /**
     * 0：管理员类型；1：业务类型
     */
    MANAGER(0),
    AGENCY(1);
    /**
     * 用户类型
     */
    private int type;

    UserType(int type) {
        this.type = type;
    }
}
