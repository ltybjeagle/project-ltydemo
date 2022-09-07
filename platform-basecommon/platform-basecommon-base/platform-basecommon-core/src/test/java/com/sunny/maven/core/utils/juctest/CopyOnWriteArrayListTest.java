package com.sunny.maven.core.utils.juctest;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author SUNNY
 * @description: CopyOnWriteArrayList测试
 * @create: 2022-08-22 15:13
 */
@Slf4j
public class CopyOnWriteArrayListTest {

    @Test
    public void CopyOnWriteArrayListTest() throws InterruptedException {
        CopyOnWriteArrayList<Integer> cowAl = new CopyOnWriteArrayList<>();
        for (int i = 0; i < 10; i++) {
            cowAl.add(i);
        }

        PutThread pt = new PutThread(cowAl);
        pt.start();
        Iterator<Integer> it = cowAl.iterator();
        while (it.hasNext()) {
            log.info("{} ", it.next());
        }
        log.info("*****************************************");
        Thread.sleep(200);
        Iterator<Integer> it1 = cowAl.iterator();
        while (it1.hasNext()) {
            log.info("{} ", it1.next());
        }
        Thread.sleep(10000);
    }

    class PutThread extends Thread {
        private CopyOnWriteArrayList<Integer> cowAl;
        public PutThread(CopyOnWriteArrayList<Integer> cowAl) {
            this.cowAl = cowAl;
        }
        @Override
        public void run() {
            try {
                for (int i = 100; i < 110; i++) {
                    cowAl.add(i);
                    Thread.sleep(50);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
