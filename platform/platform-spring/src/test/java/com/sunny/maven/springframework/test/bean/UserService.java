package com.sunny.maven.springframework.test.bean;

/**
 * @author SUNNY
 * @description: 模拟用户 Bean 对象
 * @create: 2023-02-25 22:41
 */
public class UserService {

    private String name;

    public UserService(String name) {
        this.name = name;
    }

    public void queryUserInfo() {
        System.out.println("查询用户信息：" + name);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("");
        sb.append(name);
        return sb.toString();

    }
}
