package com.sunny.maven.base;

import java.util.Vector;

/**
 * @author SUNNY
 * @description: 堆测试
 * @create: 2021-12-21 12:10
 */
public class JavaHeapTest {
    /**
     *
     * @param args
     */
    public static void main(String[] args) {
        /*
         * -Xmx 设置最大堆
         * -Xms 设置最小堆
         * -Xmn 设置新生代
         * -Xss 设置线程栈大小
         * -XX:MetaspaceSize 设置元空间大小
         * -XX:MaxMetaspaceSize 设置最大元空间
         * -XX:SurvivorRatio 设置新生代比例（eden与s0、s1）
         * -verbose:gc 输出GC
         * -XX:+PrintGCDetails 输出GC明细
         */
        Vector<byte[]> v = new Vector<>();
        for (int i = 1, j = 10; i <= j; i++) {
            // 默认创建1M
            byte[] b = new byte[1024 * 1024];
            v.add(b);
            if (v.size() == 3) {
                v.clear();
            }
        }
        System.out.println("Max memory : " + Runtime.getRuntime().maxMemory() / 1024 / 1024 + "M");
    }
}
