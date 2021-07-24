package com.sunny.maven.util;

import com.sunny.maven.util.dto.UserDto;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.ImmutableTriple;

import java.util.Date;
import java.util.Map;
import java.util.Objects;

/**
 * @author SUNNY
 * @description: Objects工具集
 * @create: 2021-07-23 16:58
 */
public class ObjectDemo {
    public static void main(String ...args) {
        ObjectDemo objectDemo = new ObjectDemo();
        objectDemo.ObjectOption();
        objectDemo.commonLangOption();
        objectDemo.commonBeanOption();
    }

    /**
     * Apache Common BeanUtils 工具
     */
    public void commonBeanOption() {
        /*
         * BeanUtils BEAN对象工具类
         */
        try {
            UserDto userDto = new UserDto();
            BeanUtils.setProperty(userDto, "id", 1);
            BeanUtils.setProperty(userDto, "name", "Sunny");
            System.out.println(BeanUtils.getProperty(userDto, "name"));
            System.out.println(userDto);

            // bean对象转换Map
            Map<String, String> map = BeanUtils.describe(userDto);
            System.out.println(map);
            UserDto user = new UserDto();
            BeanUtils.populate(user, map);
            System.out.println(user);
        } catch (Exception e) {
        }
    }

    /**
     * Apache Common lang3 工具
     */
    public void commonLangOption() {
        /*
         * 包装临时对象
         */
        ImmutablePair<Integer, String> pair = ImmutablePair.of(1, "test");
        System.out.println(pair.left + "," + pair.getRight());
        ImmutableTriple<Integer, String, Date> triple = ImmutableTriple.of(1, "test", new Date());
        System.out.println(triple.left + "," + triple.getMiddle() + "," + triple.getRight());

        /*
         * 字符串处理
         */
        // 首字母转成大写
        String str = "test";
        String str_rlt = StringUtils.capitalize(str);
        System.out.println(str_rlt);
        // 重复拼接字符串
        String str_db = StringUtils.repeat("ab", 2);
        System.out.println(str_db);
    }

    /**
     * JDK原生应用
     */
    public void ObjectOption() {
        /*
         * 对象比较
         */
        // 比较两个字符串是否相等，忽略大小写
        String obj1 = "123", obj2 = "456";
        System.out.println(Objects.equals(obj1, obj2));
    }
}
