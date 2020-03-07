package com.sunny.maven.datastruct;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @description: 列表数据结构（底层数据结构：数组、链表）
 * @author: Sunny
 * @date: 2019/9/11
 */
public class ListStruct {
    /**
     * CopyOnWriteArrayList: JUC包里的并发集合
     * 列表特性：
     *      1、更新操作（增/删/改）先获取独占锁，在拷贝原数组数据到一个新数组，在新数组里更新数据，
     *      在将新数组地址赋值给原数组地址
     *      2、查询操作不需要使用锁，因更新操作是在新数组上操作，所以不影响查询操作
     *      3、使用迭代器遍历列表数据时，同查询一样，更新操作不影响迭代器查询
     *      4、弱一致性集合
     *      5、底层数据结构：数组
     */
    private static volatile CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();

    public static void main(String ...args) throws Exception{
        /**
         * 验证：多线程下，主线程main迭代遍历，子线程更新理列表集合。
         * 结果：更新不影响迭代器遍历
         * 结论：CopyOnWriteArrayList特性
         */
        arrayList.add("hello");
        arrayList.add("hello1");
        arrayList.add("hello2");
        arrayList.add("hello3");
        arrayList.add("hello4");
        Thread thread = new Thread(() -> {
            arrayList.set(1, "hello5");
            arrayList.remove(2);
            arrayList.remove(3);
        });
        Iterator<String> itr = arrayList.iterator();
        thread.start();
        // 阻塞，等待子线程执行完毕
        thread.join();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }
}
