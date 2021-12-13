package com.sunny.maven.spring.xmlbean;

import java.util.Objects;

/**
 * @author SUNNY
 * @description: Spring Bean
 * @create: 2021-07-27 23:29
 */
public class PersonService {
    /**
     * 名称
     */
    private String name;
    /**
     * 年龄
     */
    private int age;

    public PersonService(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public PersonService() {
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PersonService that = (PersonService) o;
        return age == that.age &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age);
    }

    /**
     *
     * @return String
     */
    @Override
    public String toString() {
        return "PersonService{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
