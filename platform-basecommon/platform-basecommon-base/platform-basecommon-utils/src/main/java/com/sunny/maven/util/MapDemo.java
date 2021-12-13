package com.sunny.maven.util;

import com.google.common.collect.*;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @author SUNNY
 * @description: 键值集合工具集
 * @create: 2021-07-24 15:42
 */
public class MapDemo {
    public static void main(String ...args) {
        MapDemo mapDemo = new MapDemo();
        mapDemo.guavaOption();
    }

    /**
     * guava 工具
     */
    public void guavaOption() {
        // 创建集合
        Map<String, String> map = Maps.newHashMap();
        System.out.println(map);
        Set<String> set = Sets.newHashSet();
        System.out.println(set);

        // Multimap 一个key可以映射多个value的HashMap
        Multimap<String, Integer> multimap = ArrayListMultimap.create();
        multimap.put("key", 1);
        multimap.put("key", 2);
        Collection<Integer> values = multimap.get("key");
        System.out.println(values);
        System.out.println(multimap);
        Map<String, Collection<Integer>> collectionMap = multimap.asMap();
        System.out.println(collectionMap);

        // BiMap 一种连value也不能重复的HashMap
        BiMap<String, String> biMap = HashBiMap.create();
        biMap.put("key", "value");
        System.out.println(biMap);
        // 翻转key/value
        BiMap<String, String> inverse = biMap.inverse();
        System.out.println(inverse);

        // Table 一种有两个key的HashMap
        Table<Integer, String, String> table = HashBasedTable.create();
        table.put(18, "男", "test1");
        table.put(18, "女", "test2");
        System.out.println(table.get(18, "男"));
        // 一个二维的Map，可以查看行数据
        Map<String, String> row = table.row(18);
        System.out.println(row);
        // 查看列数据
        Map<Integer, String> column = table.column("男");
        System.out.println(column);

        // Multiset 一种用来计数的Set
        Multiset<String> multiset = HashMultiset.create();
        multiset.add("apple");
        multiset.add("apple");
        multiset.add("orange");
        System.out.println(multiset.count("apple"));
        // 查看去重的元素
        Set<String> set1 = multiset.elementSet();
        System.out.println(set1);
        // 非去重遍历
        Iterator<String> it = multiset.iterator();
        while (it.hasNext()) {
            System.out.println(it.next());
        }
        // 手动设置某个元素出现的次数
        multiset.setCount("apple", 5);
        System.out.println(multiset);
    }
}
