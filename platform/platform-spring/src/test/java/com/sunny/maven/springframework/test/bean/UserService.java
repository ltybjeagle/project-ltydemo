package com.sunny.maven.springframework.test.bean;

import com.sunny.maven.springframework.beans.factory.DisposableBean;
import com.sunny.maven.springframework.beans.factory.InitializingBean;

/**
 * @author SUNNY
 * @description: 模拟用户 Bean 对象
 * @create: 2022-12-14 10:17
 */
public class UserService implements InitializingBean, DisposableBean {

    private String id;
    private String company;
    private String location;
    private UserDao userDao;

    @Override
    public void destroy() throws Exception {
        System.out.println("执行：UserService.destroy");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("执行：UserService.afterPropertiesSet");
    }

    public String queryUserInfo() {
        return userDao.queryUserName(id) + "," + company + "," + location;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }
}
