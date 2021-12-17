package com.sunny.maven.facade.feign.client;

import com.sunny.maven.facade.api.UserRemoteClient;
import com.sunny.maven.facade.feign.config.FeignConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author SUNNY
 * @description: 用户中心Feign客户端
 * @create: 2021-12-16 15:13
 */
@Component
@FeignClient(value = "usercenter-service", configuration = FeignConfiguration. class)
public interface UserRemoteFeignClient extends UserRemoteClient {
}
