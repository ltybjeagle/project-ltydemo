package com.sunny.maven.core.common.context;

import java.util.Map;

/**
 * @author SUNNY
 * @description: 用户会话上下文
 * @create: 2022-01-17 12:18
 */
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
     * 请求对象类型
     */
    private String language;
    /**
     * 用户信息
     */
    private Map<String, Object> userDto;

    public String getDatasourceKey() {
        return datasourceKey;
    }

    public void setDatasourceKey(String datasourceKey) {
        this.datasourceKey = datasourceKey;
    }

    public Map<String, Object> getUserDto() {
        return userDto;
    }

    public void setUserDto(Map<String, Object> userDto) {
        this.userDto = userDto;
    }

    public String getTokenId() {
        return tokenId;
    }

    public void setTokenId(String tokenId) {
        this.tokenId = tokenId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
