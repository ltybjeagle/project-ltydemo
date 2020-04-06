package com.sunny.maven.entity;

import java.io.Serializable;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/4/5 17:22
 * @description：测试实体类
 * @modified By：
 * @version: 1.0.0
 */
public class Person implements Serializable {

    private static final long serialVersionUID = 4679085930151801502L;
    private String name;
    private int age;
    private String sex;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", sex='" + sex + '\'' +
                '}';
    }
}
