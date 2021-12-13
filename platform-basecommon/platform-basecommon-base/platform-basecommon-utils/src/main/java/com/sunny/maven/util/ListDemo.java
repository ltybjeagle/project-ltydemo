package com.sunny.maven.util;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author SUNNY
 * @description: 列表工具集
 * @create: 2021-07-22 20:15
 */
public class ListDemo {

    public static void main(String ...args) {
        ListDemo demo = new ListDemo();
        demo.listOption();
        demo.commonCollectionsOption();
        demo.guavaOption();
    }

    /**
     * guava 工具
     */
    public void guavaOption() {
        // 创建集合
        List<String> list = Lists.newArrayList();
        System.out.println(list.size());
        List<Integer> intList = Lists.newArrayList(1, 2, 3);
        System.out.println(intList);

        // 反转list
        List<Integer> reverse = Lists.reverse(intList);
        System.out.println(reverse);

        // list集合元素太多，可以分成若干个集合，每个集合n个元素
        List<List<Integer>> partition = Lists.partition(intList, 1);
        System.out.println(partition);
    }

    /**
     * Apache Common Collections 工具
     */
    public void commonCollectionsOption() {
        // 两个集合取交集
        Collection<String> collection = CollectionUtils.retainAll(list1, list2);
        System.out.println(collection);
        // 两个集合取并集
        collection = CollectionUtils.union(list1, list2);
        System.out.println(collection);
        // 两个集合取差集
        collection = CollectionUtils.subtract(list1, list2);
        System.out.println(collection);
    }

    /**
     * JDK原生应用
     */
    public void listOption() {
        /*
         * 如何把list集合拼接成以逗号分隔的字符串 a,b,c
         */
        List<String> list = Arrays.asList("a", "b", "c");

        // 第一种方法，可以用stream流
        String join = list.stream().collect(Collectors.joining(","));
        System.out.println(join);

        // 第二种方法，其实String也有join方法可以实现这个功能
        String join_Str = String.join(",", list);
        System.out.println(join_Str);

        /*
         * 两个List集合取交集
         */
        list1.retainAll(list2);
        System.out.println(list1);
    }

    /**
     * 测试集合
     */
    private static final List<String>  list1 = new ArrayList<>();
    private static final List<String>  list2 = new ArrayList<>();
    static {
        list1.add("a");
        list1.add("b");

        list2.add("a");
        list2.add("b");
    }
}
