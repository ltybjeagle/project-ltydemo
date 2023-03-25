package com.sunny.maven.springframework.beans;

import java.util.ArrayList;
import java.util.List;

/**
 * @author SUNNY
 * @description: 属性值集合
 * @create: 2022-12-15 16:53
 */
public class PropertyValues {
    private final List<PropertyValue> propertyValueList = new ArrayList<>();

    public void addPropertyValue(PropertyValue pv) {
        this.propertyValueList.add(pv);
    }

    public PropertyValue[] getPropertyValues() {
        return this.propertyValueList.toArray(new PropertyValue[0]);
    }

    public PropertyValue getPropertyValue(String PropertyName) {
        for (PropertyValue pv : propertyValueList) {
            if (pv.getName().equals(PropertyName)) {
                return pv;
            }
        }
        return null;
    }
}
