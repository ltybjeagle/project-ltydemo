package com.sunny.maven.springframework.test.bean;

/**
 * @author SUNNY
 * @description: 模拟用户 Bean 对象
 * @create: 2022-12-14 10:17
 */
public class UserService {

    private String id;

    private UserDao userDao;

    public void queryUserInfo() {
        System.out.println("查询用户信息: " + userDao.queryUserName(id));
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
