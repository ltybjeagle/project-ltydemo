package com.liuty.maven.collection;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 列表类集合
 */
public class ListDemo {
    /**
     * CopyOnWriteArrayList: 并发包里的并发集合
     * 1、更新操作（增/删/改）先获取独占锁，在拷贝原数组数据到一个新数组，在新数组里更新数据，
     * 在将新数组地址赋值给原数组地址，替换地址时判断是否有线程在对数据操作（包含查询），
     * 2、查询操作不需要使用锁，
     */
    private static volatile CopyOnWriteArrayList<String> arrayList = new CopyOnWriteArrayList<>();

    public static void main(String ...args) throws Exception{
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
        thread.join();
        while (itr.hasNext()) {
            System.out.println(itr.next());
        }
    }
}
