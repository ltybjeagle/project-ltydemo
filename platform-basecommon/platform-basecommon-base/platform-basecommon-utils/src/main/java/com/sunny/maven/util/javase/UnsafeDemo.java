package com.sunny.maven.util.javase;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author ：SUNNY
 * @date ：Created in 2021/1/11 9:57
 * @description：Unsafe
 * CAS操作机制：底层使用Unsafe类实现
 *  ABA问题：线程1获取变量X的值A后，在执行CAS操作前，线程2使用CAS修改了变量X的值为B，
 *  然后又使用CAS修改了变量X值为A，这时线程1执行CAS操作的时X变量值为A，
 *  但是这个时候的变量值已经不是之前获取的值了，这就是ABA问题。
 *  使用时间戳可以解决ABA问题。
 * @modified By：
 * @version: 1.0.0
 */
public class UnsafeDemo {
    /**
     * Unsafe本地函数操作对象方法类,属于rt.jar
     * getUnsafe()获取Unsafe实例，判断类加载器是否使用Bootstrap类加载器，如果不是抛出异常
     * JDK控制Unsafe实例的获取 static final Unsafe unsafe = Unsafe.getUnsafe();
     * 使用的时候可以使用反射特性获取Unsafe实例
     */
    static final Unsafe unsafe;
    static final long stateOffset;
    static {
        try {
            /**
             * 使用反射获取Unsafe的成员变量theUnsafe
             */
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            /**
             * 设置为可存取
             */
            field.setAccessible(true);
            /**
             * 获取该变量的值就是Unsafe实例
             */
            unsafe = (Unsafe) field.get(null);
            /**
             * 获取TestUnsafe类中state属性在类中的偏移量
             */
            stateOffset = unsafe.objectFieldOffset(TestUnsafe.class.getDeclaredField("state"));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            throw new Error(ex);
        }
    }
    public static void main(String ...args) {
        TestUnsafe test = new TestUnsafe();
        /**
         * 使用CAS方式更新变量的值
         * compareAndSwapLong(对象, 属性偏移量, 原始值, 更新值)
         * 更新逻辑：对象的属性是否等于原始值，如果相等使用更新值更新并返回true，否则返回false
         */
        boolean success = unsafe.compareAndSwapLong(test, stateOffset, 0, 1);
        System.out.println(success);

        // 注册停服回调方法，处理系统逻辑
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // 停服前处理资源
        }));
    }

    public static class TestUnsafe {
        private volatile long state = 0;
    }
}
