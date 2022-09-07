package com.sunny.maven.core.utils.atomic;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @author SUNNY
 * @description: 原子类测试验证
 * @create: 2022-08-11 17:06
 */
@Slf4j
public class AtomicUtilsTest {
    private static AtomicStampedReference<Integer> atomicStampedRef =
            new AtomicStampedReference<>(1, 0);

    @Test
    public void AtomicStampedReferenceTest() throws InterruptedException {
        Thread main = new Thread(() -> {
            log.info("操作线程 {}，初始值 a = {}", Thread.currentThread(), atomicStampedRef.getReference());
            // 获取当前标识别
            int stamp = atomicStampedRef.getStamp();
            // 等待1秒 ，以便让干扰线程执行
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            // 此时expectedReference未发生改变，但是stamp已经被修改了,所以CAS失败
            boolean isCASSuccess =
                    atomicStampedRef.compareAndSet(1, 2, stamp, stamp + 1);
            log.info("操作线程 {}，CAS操作结果 {}", Thread.currentThread(), isCASSuccess);
        }, "主操作线程");
        Thread other = new Thread(() -> {
            Thread.yield();
            atomicStampedRef.compareAndSet(1, 2,
                    atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
            log.info("操作线程 {}，【increment】,值 = {}", Thread.currentThread(), atomicStampedRef.getReference());
            atomicStampedRef.compareAndSet(2, 1,
                    atomicStampedRef.getStamp(), atomicStampedRef.getStamp() + 1);
            log.info("操作线程 {}，【increment】,值 = {}", Thread.currentThread(), atomicStampedRef.getReference());
        }, "干扰线程");
        main.start();
        other.start();
        Thread.sleep(100000);
    }
}
