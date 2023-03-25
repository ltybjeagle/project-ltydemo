package com.sunny.maven.springframework.beans;

/**
 * @author SUNNY
 * @description: Bean 属性信息
 * @create: 2022-12-15 16:21
 */
public class PropertyValue {
    /**
     * 属性名称
     */
    private final String name;
    /**
     * 属性值
     */
    private final Object value;

    public PropertyValue(String name, Object value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Object getValue() {
        return value;
    }
}
