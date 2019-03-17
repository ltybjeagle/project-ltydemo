package com.liuty.maven.multithread;

import java.util.concurrent.atomic.AtomicLong;

/**
 * 原子类：底层基于CAS逻辑实现原子性，比锁性能好
 * 底层使用Unsafe类实现原子操作
 * 高并发下性能还是会存在问题（大量线程更新同一个变量失败重试操作）
 *
 * LongAdder是为了解决Atomic类高并发下的性能问题
 * 处理方案：将一个原子类型分成多个原子类型，由多个原子类型分摊线程，减少多线程的竞争，提高性能
 * 多个原子类型数据使用cell数组存储，数组容量为2的N次方
 * 变量值：value = base + (for i = 0, n then cells[i])2
 * 内部属性：
 * base：基础值
 * cellsBusy：状态值，值集（0、1），用于控制cell数组初始化和扩容
 * cells数组：原子值数组
 *
 * LongAdder是LongAccumulator类的一个特例
 */
public class AtomicDemo {
    // 创建原子类
    private static AtomicLong atomicLong = new AtomicLong();

    private static Integer[] arrayOne = new Integer[] {0,1,2,3,0,5,6,0,56,0};
    private static Integer[] arrayTwo = new Integer[] {10,1,2,3,0,5,6,0,56,0};

    public static void main(String ...args) throws Exception {
        Thread threadOne = new Thread(() -> {
            int size = arrayOne.length;
            for (int i = 0; i < size; i++) {
                if (arrayOne[i].intValue() == 0) {
                    // 自加1
                    atomicLong.incrementAndGet();
                }
            }
        });
        Thread threadTwo = new Thread(() -> {
            for (int i = 0, size = arrayTwo.length; i < size; i++) {
                if (arrayTwo[i].intValue() == 0) {
                    // 自加1
                    atomicLong.incrementAndGet();
                }
            }
        });

        threadOne.start();
        threadTwo.start();

        threadOne.join();
        threadTwo.join();

        System.out.println(" count 0 :" + atomicLong.get());
    }
}
