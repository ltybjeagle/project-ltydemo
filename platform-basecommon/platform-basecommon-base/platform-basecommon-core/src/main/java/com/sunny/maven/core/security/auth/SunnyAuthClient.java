package com.sunny.maven.core.security.auth;

import com.sunny.maven.core.common.constant.CommonConstant;
import com.sunny.maven.core.common.enums.ReturnEnum;
import com.sunny.maven.core.exception.AppException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Objects;

/**
 * @author SUNNY
 * @description: 认证服务验证
 * @create: 2022-01-14 17:46
 */
@Component
public class SunnyAuthClient {
    private static final Logger logger = LoggerFactory.getLogger(SunnyAuthClient.class);

    /**
     * 认证服务地址
     */
    @Value("${fasp.auth.server.address:119.3.156.206}")
    private String faspAuthServerAddress;
    /**
     * 请求对象
     */
    private RestTemplate restTemplate;

    /**
     * 通过认证服务获取用户信息
     * @param token_id 认证ID
     * @return Map
     */
    public Map<String, Object> getLoginInfo(String token_id) {
        String url = String.format("http://%s/fasp/restapi/v1/sec/tokenids/%s/userinfo", faspAuthServerAddress,
                token_id);
        logger.info("网关开始认证:{}", url);
        Map<String, Object> result;
        try {
            ResponseEntity<Map> responseEntity = restTemplate.getForEntity(url, Map.class);
            result = responseEntity.getBody();
        } catch (Exception e1) {
            logger.error(String.format("认证中心服务端获取登录用户信息失败，请求url:%s", url) + e1.getMessage());
            throw new AppException.AppExceptionBuilder().code(ReturnEnum.UAC502.getMsgCode()).build();
        }
        if (Objects.isNull(result) || !Boolean.parseBoolean(String.valueOf(result.get(CommonConstant.SUCCESS)))) {
            throw new AppException.AppExceptionBuilder().code(ReturnEnum.UAC502.getMsgCode()).build();
        }
        return result;
    }

    /**
     * 构造函数
     * @param restTemplate restTemplate
     */
    @Autowired
    public SunnyAuthClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
}
