package com.sunny.maven.core.security.auth;

import com.sunny.maven.core.util.JsonUtils;
import com.sunny.maven.core.util.encryption.AesUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author SUNNY
 * @description: 认证服务验证
 * @create: 2022-01-14 22:33
 */
@Component
public class SunnyAuthClientPO {

    private SunnyAuthClient sunnyAuthClient;
    /**
     * 通过认证服务获取用户信息
     * @param tokenId 认证ID
     * @return Map
     */
    public Map<String, Object> getLoginInfo(String tokenId) {
        Map<String, Object> result = sunnyAuthClient.getLoginInfo(tokenId);
        return JsonUtils.parse(AesUtil.decryptByAes((String) result.get("data")), Map.class);
    }

    /**
     * 构造函数
     * @param sunnyAuthClient faspAuthClient
     */
    @Autowired
    public SunnyAuthClientPO(SunnyAuthClient sunnyAuthClient) {
        this.sunnyAuthClient = sunnyAuthClient;
    }
}
