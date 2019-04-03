package com.liuty.maven.service;

import com.liuty.maven.entity.UserEntity;
import com.liuty.maven.service.feign.UserFeignClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    UserFeignClient userFeignClient;

    public UserEntity findUserEntityById(String id) throws Exception {
        long startTime = System.currentTimeMillis();
        UserEntity userEntity = this.userFeignClient.findUserById(id);
        long endTime = System.currentTimeMillis();
        logger.info("springcloud-rest-provider服务调用时间：{} ms", (endTime - startTime));
        return userEntity;
    }
}
/**
 * 使用熔断器和负载均衡器实现
 * @Bean
 * @LoadBalanced
 * RestTemplate getRestTemplate() {
 *      return new RestTemplate();
 * }
 *
 * @Service
 * public class UserService {
 *
 *     @Autowired
 *     RestTemplate restTemplate;
 *
 *     @HystrixCommand(fallbackMethod = "findUserEntityByIdFallback", ignoreExceptions = {},
 *             commandKey = "userByIdKey", groupKey = "userGroup", threadPoolKey = "userThreadPool",
 *             commandProperties = {})
 *     public UserEntity findUserEntityById(String id) throws Exception {
 *         long startTime = System.currentTimeMillis();
 *         UserEntity userEntity = this.restTemplate.getForObject(
 *                 "http://springcloud-rest-provider/userRest/user/findbyid/{1}", UserEntity.class, id);
 *         long endTime = System.currentTimeMillis();
 *         System.out.println("springcloud-rest-provider服务调用时间：" + (endTime - startTime) + "ms");
 *         return userEntity;
 *     }
 *
 *     public UserEntity findUserEntityByIdFallback(Throwable e) {
 *         UserEntity defaultUser = new UserEntity();
 *         defaultUser.setGuid(-1);
 *         defaultUser.setName("默认用户");
 *         defaultUser.setCode("000000");
 *         return defaultUser;
 *     }
 * }
 */
