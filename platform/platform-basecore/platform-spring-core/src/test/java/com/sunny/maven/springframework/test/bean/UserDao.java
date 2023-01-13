package com.sunny.maven.springframework.test.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author SUNNY
 * @description: 模拟用户DAO类
 * @create: 2022-12-15 17:24
 */
public class UserDao {
    private static Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("10001", "SUNNY");
        hashMap.put("10002", "CANDY");
        hashMap.put("10003", "LILY");
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }
}
