package com.sunny.maven.ioc;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyEditorSupport;
import java.util.stream.Stream;

/**
 * @author ：SUNNY
 * @date ：Created in 2020/8/13 23:51
 * @description：java beans
 * @modified By：
 * @version: 1.0.0
 */
public class BeanInfoDemo {

    public static void main(String ...args) throws IntrospectionException {
        BeanInfo beanInfo = Introspector.getBeanInfo(Person.class, Object.class);
        Stream.of(beanInfo.getPropertyDescriptors())
                .forEach(propertyDescriptor -> {
                    //System.out.println(propertyDescriptor);
                    Class<?> propertyType = propertyDescriptor.getPropertyType();
                    String propertyName = propertyDescriptor.getName();
                    if ("age".equals(propertyName)) {
                        // String --> Integer
                        propertyDescriptor.setPropertyEditorClass(StringToIntergerPropertyEditor.class);
                    }
                });
    }

    static class StringToIntergerPropertyEditor extends PropertyEditorSupport {
        public void setAsText(String text) {
            Integer value = Integer.valueOf(text);
            setValue(value);
        }
    }
}
