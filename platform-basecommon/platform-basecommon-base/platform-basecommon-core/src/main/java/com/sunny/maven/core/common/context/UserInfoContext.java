package com.sunny.maven.core.common.context;

import lombok.Data;

import java.util.Map;

/**
 * @author SUNNY
 * @description: 用户会话上下文
 * @create: 2022-09-21 13:50
 */
@Data
public class UserInfoContext {
    /**
     * 数据源关联key
     */
    private String datasourceKey;
    /**
     * 登录用户tokenId
     */
    private String tokenId;
    /**
     * 用户信息
     */
    private Map<String, Object> userDto;
}
