package com.liuty.maven.thread;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * Created by liutianyang on 2018/11/11.
 */
public class UnsafeTest {

    static final Unsafe unsafe;

    static final long statOffset;

    private volatile long state = 0;

    static {
        try {
            // Unsafe类只能使用Bootstrap类加载器加载
            // 可以通过反射获取Unsafe对象
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            unsafe = (Unsafe) field.get(null);
            statOffset = unsafe.objectFieldOffset(UnsafeTest.class.getDeclaredField("state"));
        } catch (Exception ex) {
            System.out.println(ex.getLocalizedMessage());
            throw  new Error(ex);
        }
    }

    public static void main(String ...args) {
        UnsafeTest test = new UnsafeTest();
        Boolean success = unsafe.compareAndSwapInt(test, statOffset, 0 , 1);
        System.out.println(success);
    }
}
