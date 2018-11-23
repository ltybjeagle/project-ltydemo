package com.liuty.maven.service;

import com.liuty.maven.entity.UserEntity;
import com.liuty.maven.service.feign.UserFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserFeignClient userFeignClient;

    /**
     * 根据用户ID查询用户数据
     * @param id
     * @return
     */
    public UserEntity findUserEntityById(String id) {
        long startTime = System.currentTimeMillis();
        UserEntity userEntity = this.userFeignClient.findUserEntityById(id);
        long endTime = System.currentTimeMillis();
        System.out.println("microservice-provider-user服务调用时间：" + (endTime - startTime) + "ms");
        StringBuilder stringBuilder = new StringBuilder();
        UserEntity postUserEntity = new UserEntity();
        postUserEntity.setGuid(id);
        stringBuilder.append(userEntity).append("\n").append(this.userFeignClient.findUserByRequestHeader(id))
                .append("\n").append(this.userFeignClient.modifyOneUser(postUserEntity));
        System.out.println(stringBuilder.toString());
        return userEntity;
    }
    /**
     * 根据用户名、密码查询用户
     * @param name
     * @param password
     * @return
     *
    @HystrixCommand(fallbackMethod = "findUserByNameAndPwFallBack" , ignoreExceptions = {}
            , commandKey = "findUserByNameAndPw", groupKey = "userGroupKey", threadPoolKey = "userThreadKey")
    public UserEntity findUserByNameAndPw(String name, String password) {
        UserEntity userEntity = this.restTemplate.getForObject(
                "http://microservice-provider-user/user/findUserByNamePw/{1}/{2}"
                , UserEntity.class, name, password);
        return userEntity;
    }*/

    /**
     * 根据用户ID查询用户数据
     * @param id
     * @return
     *
    @CacheResult(cacheKeyMethod = "getCacheKeyUserId")
    @HystrixCommand(fallbackMethod = "findUserByIdFallBack" , ignoreExceptions = {}, commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "1000")
    }, commandKey = "findUserEntityById", groupKey = "userGroupKey", threadPoolKey = "userThreadKey")
    public UserEntity findUserEntityById(String id) {
        long startTime = System.currentTimeMillis();
        UserEntity userEntity = this.restTemplate.getForEntity(
                "http://microservice-provider-user/user/findbyid/" + id
                , UserEntity.class).getBody();
        long endTime = System.currentTimeMillis();
        System.out.println("microservice-provider-user服务调用时间：" + (endTime - startTime) + "ms");
        return userEntity;
    }*/

    /**
     * 根据ID查询用户，使用延迟批量查询（在100ms周期内合并查询请求，使用findUsersByIds批量方法查询）
     * @param id
     * @return
     *
    @HystrixCollapser(batchMethod = "findUsersByIds", collapserProperties = {
            @HystrixProperty(name="timerDelayInMilliseconds", value = "100")
    })
    public UserEntity findUserById(String id) {
        return null;
    }*/

    /**
     * 批量查询用户数据
     * @param ids
     * @return
     *
    @HystrixCommand(commandKey = "findUsersByIds", groupKey = "userGroupKey", threadPoolKey = "userThreadKey")
    public List<UserEntity> findUsersByIds(List<String> ids) {
        return this.restTemplate.getForObject("http://microservice-provider-user/user/findAll?ids={1}"
                , List.class, StringUtils.join(ids, ","));
    }*/

    /**
     * 更新用户数据
     * @param user
     *
    @CacheRemove(commandKey = "findUserEntityById")
    @HystrixCommand(commandKey = "modifyUser", groupKey = "userGroupKey", threadPoolKey = "userThreadKey")
    public void modifyUser(@CacheKey("id") UserEntity user) {
        this.restTemplate.postForObject("http://microservice-provider-user/user/modify", user
                , UserEntity.class);
    }*/

    /**
     * 缓存KEY函数
     * @param id
     * @return
     */
    public String getCacheKeyUserId(String id) {
        return id;
    }

    /**
     * Hystrix断路器开启，提供默认方法(findUserById)
     * @param id
     * @param e
     * @return
     */
    private UserEntity findUserByIdFallBack(String id, Throwable e) {
        System.out.println("findUserById 异常：" + e.getMessage());
        return this.getDefaultUserEntity();
    }

    /**
     * Hystrix断路器开启，提供默认方法(findUserByNameAndPw)
     * @param name
     * @param password
     * @param e
     * @return
     */
    private UserEntity findUserByNameAndPwFallBack(String name, String password, Throwable e) {
        System.out.println("findUserByNameAndPw 异常：" + e.getMessage());
        return this.getDefaultUserEntity();
    }

    private static UserEntity getDefaultUserEntity() {
        return DefaultUserEntity.defaultUser;
    }

    private static class DefaultUserEntity {
        private static UserEntity defaultUser = new UserEntity();
        static {
            defaultUser.setGuid("-1");
            defaultUser.setName("默认用户");
            defaultUser.setCode("000000");
        }
    }
}
