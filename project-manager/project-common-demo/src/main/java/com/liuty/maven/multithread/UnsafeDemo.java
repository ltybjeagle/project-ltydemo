package com.liuty.maven.multithread;

/**
 * CAS操作机制：底层使用Unsafe类实现
 * ABA问题：线程1获取变量X的值A后，在执行CAS操作前，线程2使用CAS修改了变量X的值为B，
 * 然后又使用CAS修改了变量X值为A，这时线程1执行CAS操作的时X变量值为A，
 * 但是这个时候的变量值已经不是之前获取的值了，这就是ABA问题。
 * 使用时间戳可以解决ABA问题。
 */
public class UnsafeDemo {
    public static void main(String ...args) {

    }
}
